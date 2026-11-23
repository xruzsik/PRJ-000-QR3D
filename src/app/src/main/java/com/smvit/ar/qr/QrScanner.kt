package com.smvit.ar.qr

import android.util.Log
import com.google.ar.core.Frame
import com.google.ar.core.Pose
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.NotYetAvailableException
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "QrScanner"

/** One QR sighting: where its anchor frame is in the world, and how well it fit. */
data class QrSighting(
    val payload: String,
    val worldFromAnchor: RigidPose,
    val reprojectionErrorPx: Double,
    val timestampNanos: Long,
)

/**
 * Runs ML Kit QR detection on ARCore's CPU camera image and turns each detected
 * code into a world pose. At most one image is in flight, and scans are spaced
 * [intervalNanos] apart (changeable at any time) to limit heat on mid-range phones.
 */
class QrScanner(
    /** Printed edge length of the code with this payload, or null for codes we don't know. */
    private val sizeFor: (payload: String) -> Double?,
    @Volatile var intervalNanos: Long = 200_000_000,
    /** Called with the payload of a QR code that [sizeFor] doesn't know. */
    private val onUnknown: (payload: String) -> Unit = {},
    /** World rotation of a code already being tracked, or null for a new one. */
    private val expectedRotation: (payload: String) -> Mat3? = { null },
    private val onSighting: (QrSighting) -> Unit,
) : AutoCloseable {

    private val client = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
    )
    private val busy = AtomicBoolean(false)
    private var lastScanNanos = 0L

    /** Call once per ARCore frame. */
    fun onFrame(frame: Frame) {
        val camera = frame.camera
        if (camera.trackingState != TrackingState.TRACKING) return
        if (frame.timestamp - lastScanNanos < intervalNanos) return
        if (!busy.compareAndSet(false, true)) return

        val image = try {
            frame.acquireCameraImage()
        } catch (e: NotYetAvailableException) {
            busy.set(false)
            return
        }
        lastScanNanos = frame.timestamp

        // Captured now: the detection result arrives after this frame is gone.
        val timestamp = frame.timestamp
        val worldFromCamera = camera.pose.toRigidPose()
        val intrinsics = camera.imageIntrinsics.let { ci ->
            val (fx, fy) = ci.focalLength
            val (cx, cy) = ci.principalPoint
            // Intrinsics are given for imageDimensions; rescale if the CPU image differs.
            val sx = image.width.toDouble() / ci.imageDimensions[0]
            val sy = image.height.toDouble() / ci.imageDimensions[1]
            Intrinsics(fx * sx, fy * sy, cx * sx, cy * sy)
        }

        // Rotation 0: corners stay in sensor pixel coordinates, matching the
        // intrinsics and ARCore's camera pose (both relative to the image readout).
        client.process(InputImage.fromMediaImage(image, 0))
            .addOnSuccessListener { barcodes ->
                // Still open here: the image is closed in the completion listener.
                val luma = image.planes[0]
                for (barcode in barcodes) {
                    val payload = barcode.rawValue ?: continue
                    val size = sizeFor(payload)
                    if (size == null) {
                        onUnknown(payload)
                        continue
                    }
                    val detected = barcode.cornerPoints?.takeIf { it.size == 4 }
                        ?.map { Pixel(it.x.toDouble(), it.y.toDouble()) } ?: continue

                    // ML Kit starts at the image's top-left corner, not the code's,
                    // so the corner order must be fixed before the pose means anything.
                    val expected = expectedRotation(payload)
                    val sighting = if (expected != null) {
                        orientByTracking(payload, size, detected, intrinsics, worldFromCamera, expected, timestamp)
                    } else {
                        orientByFinders(payload, size, detected, intrinsics, worldFromCamera, timestamp) { col, row ->
                            if (col < 0 || row < 0 || col >= image.width || row >= image.height) {
                                null
                            } else {
                                (luma.buffer.get(row * luma.rowStride + col * luma.pixelStride).toInt() and 0xFF).toDouble()
                            }
                        }
                    }
                    sighting?.let(onSighting)
                }
            }
            .addOnFailureListener { Log.w(TAG, "QR detection failed", it) }
            .addOnCompleteListener {
                image.close()
                busy.set(false)
            }
    }

    /**
     * For a code seen for the first time: reads its finder patterns, through a
     * first pose from ML Kit's corner order, to find which corner is really which.
     */
    private fun orientByFinders(
        payload: String,
        qrSizeMeters: Double,
        detected: List<Pixel>,
        intrinsics: Intrinsics,
        worldFromCamera: RigidPose,
        timestamp: Long,
        luminance: (col: Int, row: Int) -> Double?,
    ): QrSighting? {
        val first = QrPoseSolver.solve(detected, intrinsics, qrSizeMeters) ?: return null
        val analysis = QrOrientation.analyze(qrSizeMeters) { x, y ->
            val p = QrPoseSolver.project(first.cameraFromQr, Vec3(x, y, 0.0), intrinsics)
            if (p.u < 0 || p.v < 0) null else luminance(p.u.toInt(), p.v.toInt())
        }
        val withoutFinder = analysis?.cornerWithoutFinder
        if (withoutFinder == null) {
            Log.d(TAG, "\"$payload\": orientation undecided, skipping frame")
            return null
        }
        val observation = if (withoutFinder == 2) {
            first
        } else {
            QrPoseSolver.solve(QrOrientation.relabel(detected, withoutFinder), intrinsics, qrSizeMeters) ?: return null
        }
        return QrSighting(
            payload,
            Frames.worldFromAnchor(worldFromCamera, observation.cameraFromQr),
            observation.reprojectionErrorPx,
            timestamp,
        )
    }

    /**
     * For a code already being tracked: tries all four corner labellings and keeps
     * the one matching the code's current orientation. Unlike reading the finder
     * patterns, this survives motion blur (which left over half of the A53's
     * detections undecided while moving).
     */
    private fun orientByTracking(
        payload: String,
        qrSizeMeters: Double,
        detected: List<Pixel>,
        intrinsics: Intrinsics,
        worldFromCamera: RigidPose,
        expected: Mat3,
        timestamp: Long,
    ): QrSighting? {
        val candidates = (0..3).map { k ->
            QrPoseSolver.solve(QrOrientation.relabel(detected, k), intrinsics, qrSizeMeters)?.let {
                it to Frames.worldFromAnchor(worldFromCamera, it.cameraFromQr)
            }
        }
        val best = QrOrientation.closestToExpected(candidates.map { it?.second?.rotation }, expected)
        if (best == null) {
            Log.d(TAG, "\"$payload\": no labelling near the tracked orientation, skipping frame")
            return null
        }
        val (observation, worldFromAnchor) = candidates[best]!!
        return QrSighting(payload, worldFromAnchor, observation.reprojectionErrorPx, timestamp)
    }

    override fun close() = client.close()
}

fun Pose.toRigidPose() = RigidPose(
    Quat(qx().toDouble(), qy().toDouble(), qz().toDouble(), qw().toDouble()).toMatrix(),
    Vec3(tx().toDouble(), ty().toDouble(), tz().toDouble()),
)

fun RigidPose.toArPose(): Pose {
    val q = Quat.fromMatrix(rotation)
    return Pose(
        floatArrayOf(translation.x.toFloat(), translation.y.toFloat(), translation.z.toFloat()),
        floatArrayOf(q.x.toFloat(), q.y.toFloat(), q.z.toFloat(), q.w.toFloat()),
    )
}

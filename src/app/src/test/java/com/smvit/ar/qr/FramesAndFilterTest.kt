package com.smvit.ar.qr

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI

class FramesAndFilterTest {

    private fun assertVec(expected: Vec3, actual: Vec3, eps: Double = 1e-9) {
        assertTrue("expected $expected, got $actual", (expected - actual).norm() <= eps)
    }

    @Test
    fun quaternionRoundTripsThroughMatrix() {
        val axes = listOf(Vec3(1.0, 0.0, 0.0), Vec3(0.0, 1.0, 0.0), Vec3(0.3, -0.5, 0.8))
        val angles = listOf(0.0, 0.4, PI / 2, PI - 1e-3, PI)
        for (axis in axes) for (angle in angles) {
            val r = Mat3.rotation(axis, angle)
            val back = Quat.fromMatrix(r).toMatrix()
            for (c in 0..2) assertVec(r.column(c), back.column(c), 1e-9)
        }
    }

    @Test
    fun anchorOnCodeFacingCameraPointsUpTowardsCamera() {
        // Code square-on to the camera, 50 cm ahead; camera at the world origin
        // with identity orientation (looking down world -Z).
        val cvCameraFromQr = RigidPose(Mat3.rotation(Vec3(1.0, 0.0, 0.0), PI), Vec3(0.0, 0.0, 0.5))
        val anchor = Frames.worldFromAnchor(RigidPose.IDENTITY, cvCameraFromQr)

        assertVec(Vec3(0.0, 0.0, -0.5), anchor.translation) // in front of the camera
        assertVec(Vec3(1.0, 0.0, 0.0), anchor.rotation.column(0)) // +X: code's right = screen right
        assertVec(Vec3(0.0, 0.0, 1.0), anchor.rotation.column(1)) // +Y: out of the code, at the camera
        assertVec(Vec3(0.0, -1.0, 0.0), anchor.rotation.column(2)) // +Z: code's bottom = screen down
    }

    @Test
    fun anchorOnCodeLyingFlatIsUpright() {
        // Camera 40 cm above a table looking straight down (world -Y), image top
        // pointing along world -Z; code on the table, read upright in the image.
        val worldFromGlCamera = RigidPose(Mat3.rotation(Vec3(1.0, 0.0, 0.0), -PI / 2), Vec3(0.0, 0.4, 0.0))
        val cvCameraFromQr = RigidPose(Mat3.rotation(Vec3(1.0, 0.0, 0.0), PI), Vec3(0.0, 0.0, 0.4))
        val anchor = Frames.worldFromAnchor(worldFromGlCamera, cvCameraFromQr)

        assertVec(Vec3(0.0, 0.0, 0.0), anchor.translation, 1e-9) // on the table
        assertVec(Vec3(0.0, 1.0, 0.0), anchor.rotation.column(1)) // model up = world up
        assertVec(Vec3(0.0, 0.0, 1.0), anchor.rotation.column(2)) // faces the image bottom
    }

    @Test
    fun uprightFrameOfCodeLyingFlatIsItsOwnFrame() {
        // Flat code (+Y up), turned 40 deg about the vertical.
        val anchor = RigidPose(Mat3.rotation(Vec3(0.0, 1.0, 0.0), Math.toRadians(40.0)), Vec3(1.0, 0.8, -2.0))
        val frame = Frames.upright(anchor)
        assertVec(anchor.translation, frame.translation)
        for (c in 0..2) assertVec(anchor.rotation.column(c), frame.rotation.column(c))
    }

    @Test
    fun uprightFrameOfCodeOnAPostStandsAndFacesTheReader() {
        // Code on a post facing world +Z (towards a reader standing at +Z):
        // its +Y (normal) is world +Z and its +Z (bottom edge) points down.
        val onPost = RigidPose(
            Mat3.fromColumns(Vec3(1.0, 0.0, 0.0), Vec3(0.0, 0.0, 1.0), Vec3(0.0, -1.0, 0.0)),
            Vec3(0.0, 1.2, 0.0),
        )
        val frame = Frames.upright(onPost)
        assertVec(Vec3(0.0, 1.0, 0.0), frame.rotation.column(1)) // stands up
        assertVec(Vec3(0.0, 0.0, 1.0), frame.rotation.column(2)) // faces the reader
        assertVec(Vec3(1.0, 0.0, 0.0), frame.rotation.column(0)) // the code's right
        // An offset 1.2 m down and 0.8 m to the right lands beside the post on the ground.
        val character = frame compose RigidPose(Mat3.IDENTITY, Vec3(0.8, -1.2, 0.0))
        assertVec(Vec3(0.8, 0.0, 0.0), character.translation)
    }

    @Test
    fun uprightFrameOfTiltedCodeIgnoresTheTilt() {
        // Code on a lectern-style board, tilted 45 deg back, facing world +Z.
        val tilted = RigidPose(Mat3.rotation(Vec3(1.0, 0.0, 0.0), Math.toRadians(45.0)), Vec3.ZERO)
        val frame = Frames.upright(tilted)
        assertVec(Vec3(0.0, 1.0, 0.0), frame.rotation.column(1))
        assertVec(Vec3(0.0, 0.0, 1.0), frame.rotation.column(2))
    }

    @Test
    fun filterWaitsForAgreeingSamplesThenAverages() {
        val filter = QrPoseFilter(requiredSamples = 3)
        val base = RigidPose(Mat3.rotation(Vec3(0.0, 1.0, 0.0), 0.3), Vec3(1.0, 0.0, -1.0))
        fun jittered(dx: Double) = base.copy(translation = base.translation + Vec3(dx, 0.0, 0.0))

        assertEquals(QrPoseFilter.Result.Collecting(1, 3), filter.add("a", jittered(0.004), 0.5, 0))
        assertEquals(QrPoseFilter.Result.Collecting(2, 3), filter.add("a", jittered(-0.004), 0.5, 1))
        val stable = filter.add("a", jittered(0.0), 0.5, 2)
        assertTrue(stable is QrPoseFilter.Result.Stable)
        assertVec(base.translation, (stable as QrPoseFilter.Result.Stable).worldFromAnchor.translation, 1e-9)
    }

    @Test
    fun filterRejectsPoorFitsAndInconsistentSamples() {
        val filter = QrPoseFilter(requiredSamples = 3)
        val pose = RigidPose(Mat3.IDENTITY, Vec3.ZERO)
        assertEquals(QrPoseFilter.Result.Rejected, filter.add("a", pose, reprojectionErrorPx = 10.0, timestampNanos = 0))

        filter.add("a", pose, 0.5, 0)
        filter.add("a", pose, 0.5, 1)
        val outlier = pose.copy(translation = Vec3(0.2, 0.0, 0.0))
        val result = filter.add("a", outlier, 0.5, 2)
        assertTrue("outlier must not produce a stable pose", result is QrPoseFilter.Result.Collecting)
    }

    @Test
    fun filterDropsStaleSamples() {
        val filter = QrPoseFilter(requiredSamples = 2, maxAgeNanos = 1_000)
        val pose = RigidPose(Mat3.IDENTITY, Vec3.ZERO)
        filter.add("a", pose, 0.5, 0)
        assertEquals(QrPoseFilter.Result.Collecting(1, 2), filter.add("a", pose, 0.5, 5_000))
    }
}

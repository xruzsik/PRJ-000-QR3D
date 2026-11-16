package com.smvit.ar.qr

import kotlin.math.abs
import kotlin.math.sqrt

/** Pinhole intrinsics of the image the corners were detected in, in pixels. */
data class Intrinsics(val fx: Double, val fy: Double, val cx: Double, val cy: Double)

data class Pixel(val u: Double, val v: Double)

/**
 * Pose of a QR code relative to the camera.
 *
 * The QR frame has its origin at the code's centre, +X towards its right edge,
 * +Y towards its top edge and +Z out of the printed face, towards the viewer.
 * The camera frame is the computer-vision one: +X right, +Y down, +Z forward.
 */
data class QrObservation(val cameraFromQr: RigidPose, val reprojectionErrorPx: Double)

object QrPoseSolver {

    /**
     * Recovers the QR pose from its four corners in pixels, ordered top-left,
     * top-right, bottom-right, bottom-left as seen on the code itself (the order
     * ML Kit reports). [sizeMeters] is the printed edge length of the symbol,
     * excluding the white quiet zone. Returns null for degenerate input.
     */
    fun solve(corners: List<Pixel>, k: Intrinsics, sizeMeters: Double): QrObservation? {
        require(corners.size == 4) { "need 4 corners, got ${corners.size}" }
        val objectPoints = qrCorners(sizeMeters)

        // Homography H (with h33 = 1) from QR-plane (X, Y) to normalised image
        // coordinates: two linear equations per corner, 8 unknowns.
        val a = Array(8) { DoubleArray(8) }
        val b = DoubleArray(8)
        for (i in 0 until 4) {
            val (bx, by) = objectPoints[i]
            val x = (corners[i].u - k.cx) / k.fx
            val y = (corners[i].v - k.cy) / k.fy
            a[2 * i] = doubleArrayOf(bx, by, 1.0, 0.0, 0.0, 0.0, -bx * x, -by * x)
            b[2 * i] = x
            a[2 * i + 1] = doubleArrayOf(0.0, 0.0, 0.0, bx, by, 1.0, -bx * y, -by * y)
            b[2 * i + 1] = y
        }
        val h = solveLinear(a, b) ?: return null

        // H = s [r1 r2 t]. h33 = 1 > 0 already puts the code in front of the camera.
        val h1 = Vec3(h[0], h[3], h[6])
        val h2 = Vec3(h[1], h[4], h[7])
        val h3 = Vec3(h[2], h[5], 1.0)
        val scale = 2 / (h1.norm() + h2.norm())
        val rotation = orthonormalize(h1 * scale, h2 * scale) ?: return null
        val initial = RigidPose(rotation, h3 * scale)

        // The homography decomposition alone is noise-sensitive on oblique views
        // (tens of mm from half a pixel of corner noise), so refine it by
        // minimising the reprojection error directly.
        val pose = refine(initial, objectPoints, corners, k)
        if (pose.translation.z <= 0) return null
        return QrObservation(pose, reprojectionError(pose, objectPoints, corners, k))
    }

    /**
     * Levenberg-Marquardt over 6 parameters: a small rotation vector applied on the
     * left of the current rotation, plus a translation step. Jacobian by finite
     * differences, which is plenty for 8 residuals.
     */
    private fun refine(
        start: RigidPose,
        objectPoints: List<Pair<Double, Double>>,
        corners: List<Pixel>,
        k: Intrinsics,
    ): RigidPose {
        fun residuals(p: RigidPose) = DoubleArray(8).also { r ->
            for (i in 0 until 4) {
                val (bx, by) = objectPoints[i]
                val proj = project(p, Vec3(bx, by, 0.0), k)
                r[2 * i] = proj.u - corners[i].u
                r[2 * i + 1] = proj.v - corners[i].v
            }
        }
        fun cost(r: DoubleArray) = r.sumOf { it * it }
        fun step(p: RigidPose, d: DoubleArray): RigidPose {
            val w = Vec3(d[0], d[1], d[2])
            val angle = w.norm()
            val dr = if (angle < 1e-12) Mat3.IDENTITY else Mat3.rotation(w / angle, angle)
            return RigidPose(dr * p.rotation, p.translation + Vec3(d[3], d[4], d[5]))
        }

        var pose = start
        var r = residuals(pose)
        var lambda = 1e-3
        repeat(50) {
            val eps = 1e-7
            val jacobian = Array(6) { j ->
                val d = DoubleArray(6).also { it[j] = eps }
                val rj = residuals(step(pose, d))
                DoubleArray(8) { i -> (rj[i] - r[i]) / eps }
            } // jacobian[j][i] = d residual_i / d param_j
            val jtj = Array(6) { a -> DoubleArray(6) { b -> (0 until 8).sumOf { jacobian[a][it] * jacobian[b][it] } } }
            val jtr = DoubleArray(6) { a -> -(0 until 8).sumOf { jacobian[a][it] * r[it] } }

            while (lambda < 1e10) {
                val damped = Array(6) { a -> DoubleArray(6) { b -> jtj[a][b] * (if (a == b) 1 + lambda else 1.0) } }
                val delta = solveLinear(damped, jtr.copyOf()) ?: break
                val candidate = step(pose, delta)
                val rc = residuals(candidate)
                if (cost(rc) < cost(r)) {
                    val converged = delta.maxOf { abs(it) } < 1e-10
                    pose = candidate
                    r = rc
                    lambda = maxOf(lambda / 10, 1e-12)
                    if (converged) return pose
                    break
                }
                lambda *= 10
            }
            if (lambda >= 1e10) return pose
        }
        return pose
    }

    /** QR corners in the QR frame (z = 0), in [solve]'s corner order. */
    fun qrCorners(sizeMeters: Double): List<Pair<Double, Double>> {
        val h = sizeMeters / 2
        return listOf(-h to h, h to h, h to -h, -h to -h)
    }

    fun project(cameraFromQr: RigidPose, point: Vec3, k: Intrinsics): Pixel {
        val p = cameraFromQr.apply(point)
        return Pixel(k.fx * p.x / p.z + k.cx, k.fy * p.y / p.z + k.cy)
    }

    /**
     * Nearest rotation whose first two columns match the noisy [r1], [r2]:
     * splits the error evenly between them around their bisector.
     */
    private fun orthonormalize(r1: Vec3, r2: Vec3): Mat3? {
        val z = r1 cross r2
        if (z.norm() < 1e-9) return null
        val zn = z.normalized()
        val bisector = (r1.normalized() + r2.normalized()).normalized()
        val perpendicular = zn cross bisector
        val x = (bisector - perpendicular) / sqrt(2.0)
        val y = (bisector + perpendicular) / sqrt(2.0)
        return Mat3.fromColumns(x, y, zn)
    }

    private fun reprojectionError(
        pose: RigidPose,
        objectPoints: List<Pair<Double, Double>>,
        corners: List<Pixel>,
        k: Intrinsics,
    ): Double {
        var sum = 0.0
        for (i in 0 until 4) {
            val (bx, by) = objectPoints[i]
            val p = project(pose, Vec3(bx, by, 0.0), k)
            val du = p.u - corners[i].u
            val dv = p.v - corners[i].v
            sum += du * du + dv * dv
        }
        return sqrt(sum / 4)
    }

    /** Gaussian elimination with partial pivoting; null if singular. */
    private fun solveLinear(a: Array<DoubleArray>, b: DoubleArray): DoubleArray? {
        val n = b.size
        for (col in 0 until n) {
            val pivot = (col until n).maxBy { abs(a[it][col]) }
            if (abs(a[pivot][col]) < 1e-12) return null
            a[col] = a[pivot].also { a[pivot] = a[col] }
            b[col] = b[pivot].also { b[pivot] = b[col] }
            for (row in col + 1 until n) {
                val f = a[row][col] / a[col][col]
                for (c in col until n) a[row][c] -= f * a[col][c]
                b[row] -= f * b[col]
            }
        }
        val x = DoubleArray(n)
        for (row in n - 1 downTo 0) {
            var s = b[row]
            for (c in row + 1 until n) s -= a[row][c] * x[c]
            x[row] = s / a[row][row]
        }
        return x
    }
}

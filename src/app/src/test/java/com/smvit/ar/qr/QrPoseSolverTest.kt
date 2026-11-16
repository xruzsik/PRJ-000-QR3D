package com.smvit.ar.qr

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI
import kotlin.random.Random

class QrPoseSolverTest {

    // Roughly ARCore's 640x480 CPU image on a phone.
    private val k = Intrinsics(fx = 500.0, fy = 500.0, cx = 320.0, cy = 240.0)
    private val size = 0.10

    /** QR facing the camera squarely and upright: QR +Y is image up, +Z towards the camera. */
    private val facingCamera = Mat3.rotation(Vec3(1.0, 0.0, 0.0), PI)

    private fun corners(pose: RigidPose) =
        QrPoseSolver.qrCorners(size).map { (x, y) -> QrPoseSolver.project(pose, Vec3(x, y, 0.0), k) }

    private fun assertPoseClose(expected: RigidPose, actual: RigidPose, meters: Double, degrees: Double) {
        val dt = (expected.translation - actual.translation).norm()
        val da = Quat.fromMatrix(expected.rotation).angleTo(Quat.fromMatrix(actual.rotation)) * 180 / PI
        assertTrue("translation off by ${dt * 1000} mm", dt <= meters)
        assertTrue("rotation off by $da deg", da <= degrees)
    }

    private val poses = listOf(
        "square-on at 50 cm" to RigidPose(facingCamera, Vec3(0.0, 0.0, 0.5)),
        "off-centre at 30 cm" to RigidPose(facingCamera, Vec3(0.08, -0.05, 0.3)),
        "far at 2 m" to RigidPose(facingCamera, Vec3(0.1, 0.2, 2.0)),
        "tilted 50 deg, yawed 30 deg" to RigidPose(
            Mat3.rotation(Vec3(0.0, 1.0, 0.0), PI / 6) * Mat3.rotation(Vec3(1.0, 0.0, 0.0), PI - 50 * PI / 180),
            Vec3(-0.05, 0.1, 0.6),
        ),
        "steep 70 deg" to RigidPose(
            Mat3.rotation(Vec3(1.0, 0.0, 0.0), PI - 70 * PI / 180),
            Vec3(0.0, 0.05, 0.4),
        ),
        "rotated 90 deg in its plane" to RigidPose(
            facingCamera * Mat3.rotation(Vec3(0.0, 0.0, 1.0), PI / 2),
            Vec3(0.0, 0.0, 0.5),
        ),
        "upside down" to RigidPose(
            facingCamera * Mat3.rotation(Vec3(0.0, 0.0, 1.0), PI),
            Vec3(0.02, 0.0, 0.5),
        ),
    )

    @Test
    fun recoversExactPoseFromPerfectCorners() {
        for ((name, truth) in poses) {
            val result = solved(name, QrPoseSolver.solve(corners(truth), k, size))
            assertPoseClose(truth, result.cameraFromQr, meters = 1e-6, degrees = 1e-4)
            assertEquals(name, 0.0, result.reprojectionErrorPx, 1e-6)
        }
    }

    @Test
    fun staysAccurateWithHalfPixelCornerNoise() {
        val random = Random(42)
        val report = StringBuilder()
        var worstMm = 0.0
        var worstDeg = 0.0
        var worstPx = 0.0
        for ((name, truth) in poses.filter { it.second.translation.z <= 0.6 }) {
            var caseMm = 0.0
            var caseDeg = 0.0
            var casePx = 0.0
            repeat(200) {
                val noisy = corners(truth).map {
                    Pixel(it.u + random.nextDouble(-0.5, 0.5), it.v + random.nextDouble(-0.5, 0.5))
                }
                val result = solved(name, QrPoseSolver.solve(noisy, k, size))
                val pose = result.cameraFromQr
                caseMm = maxOf(caseMm, (truth.translation - pose.translation).norm() * 1000)
                caseDeg = maxOf(caseDeg, Quat.fromMatrix(truth.rotation).angleTo(Quat.fromMatrix(pose.rotation)) * 180 / PI)
                casePx = maxOf(casePx, result.reprojectionErrorPx)
            }
            report.append("%-30s max %.1f mm, %.2f deg, reprojection %.2f px%n".format(name, caseMm, caseDeg, casePx))
            worstMm = maxOf(worstMm, caseMm)
            worstDeg = maxOf(worstDeg, caseDeg)
            worstPx = maxOf(worstPx, casePx)
        }
        println(report)
        // Single-frame limits; QrPoseFilter averages several frames on top of this.
        // Square-on views are the worst case for tilt: perspective barely changes
        // with small tilts, so corner noise maps to ~5 deg of tilt error there,
        // versus < 1 deg for views 30-70 deg off-axis.
        assertTrue("worst translation error $worstMm mm\n$report", worstMm <= 10.0)
        assertTrue("worst rotation error $worstDeg deg\n$report", worstDeg <= 6.0)
        // Genuine detections must get past QrPoseFilter's default 3 px rejection.
        assertTrue("worst reprojection error $worstPx px\n$report", worstPx < 3.0)
    }

    private fun solved(message: String, value: QrObservation?): QrObservation {
        assertNotNull(message, value)
        return value!!
    }
}

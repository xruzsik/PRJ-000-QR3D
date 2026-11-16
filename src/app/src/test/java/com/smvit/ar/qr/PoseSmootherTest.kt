package com.smvit.ar.qr

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI
import kotlin.math.exp

class PoseSmootherTest {

    private val start = RigidPose(Mat3.IDENTITY, Vec3.ZERO)
    private val target = RigidPose(Mat3.rotation(Vec3(0.0, 1.0, 0.0), PI / 2), Vec3(0.1, 0.0, 0.0))

    @Test
    fun firstStepJumpsStraightToTheTarget() {
        val shown = PoseSmoother().step(target, 0)
        assertEquals(0.0, (shown.translation - target.translation).norm(), 1e-12)
    }

    @Test
    fun convergesWithTheTimeConstantRegardlessOfFrameRate() {
        for (fps in listOf(30, 60, 120)) {
            val smoother = PoseSmoother(timeConstantNanos = 80_000_000)
            smoother.step(start, 0)
            val frameNanos = 1_000_000_000L / fps
            var shown = start
            var now = 0L
            while (now < 80_000_000) {
                now += frameNanos
                shown = smoother.step(target, now)
            }
            // After one time constant: 63% of the way (± one frame's worth).
            val expected = 0.1 * (1 - exp(-now / 80_000_000.0))
            assertEquals("fps=$fps", expected, shown.translation.x, 1e-9)

            repeat(fps) { now += frameNanos; shown = smoother.step(target, now) } // +1 s
            assertTrue("fps=$fps", (shown.translation - target.translation).norm() < 1e-4)
            assertTrue("fps=$fps", Quat.fromMatrix(shown.rotation).angleTo(Quat.fromMatrix(target.rotation)) < 1e-3)
        }
    }

    @Test
    fun rotatesAlongTheShorterArc() {
        val a = Quat.fromMatrix(Mat3.rotation(Vec3(0.0, 0.0, 1.0), 0.1))
        val b = -Quat.fromMatrix(Mat3.rotation(Vec3(0.0, 0.0, 1.0), 0.3)) // same rotation, flipped sign
        val mid = a.nlerp(b, 0.5)
        val expected = Quat.fromMatrix(Mat3.rotation(Vec3(0.0, 0.0, 1.0), 0.2))
        assertTrue(mid.angleTo(expected) < 1e-3)
    }
}

package com.smvit.ar.companion

import com.smvit.ar.qr.Mat3
import com.smvit.ar.qr.RigidPose
import com.smvit.ar.qr.Vec3
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.random.Random

class CompanionTest {

    private val dt = 1.0 / 30
    private val ground = 0.0
    private val lookingAhead = Vec3(0.0, 0.0, -1.0)

    private fun companionAt(x: Double, y: Double, z: Double, seed: Int = 1) =
        Companion(RigidPose(Mat3.IDENTITY, Vec3(x, y, z)), bodyLength = 0.8, random = Random(seed))

    private fun horizontalDistance(a: Vec3, b: Vec3) = Math.hypot(a.x - b.x, a.z - b.z)

    @Test
    fun hopsDownFromTheTableOntoTheFloor() {
        val c = companionAt(0.0, 0.75, -1.0)
        repeat(30) { c.step(dt, Vec3(0.0, 1.3, 0.0), lookingAhead, ground) } // 1 s
        assertEquals(ground, c.position.y, 0.01)
    }

    @Test
    fun gallopsAfterAPlayerWhoWalksAwayAndCatchesUp() {
        val c = companionAt(0.0, 0.0, -1.0)
        var player = Vec3(0.0, 1.3, 0.0)
        var ran = false
        // The player walks 8 m along +X at 1.3 m/s.
        repeat((8 / 1.3 / dt).toInt()) {
            player += Vec3(1.3 * dt, 0.0, 0.0)
            c.step(dt, player, Vec3(1.0, 0.0, 0.0), ground)
            if (c.gait == Companion.Gait.RUN) ran = true
        }
        repeat(150) { c.step(dt, player, Vec3(1.0, 0.0, 0.0), ground) } // 5 s to settle
        assertTrue("never galloped", ran)
        assertTrue("left behind: ${horizontalDistance(c.position, player)} m",
            horizontalDistance(c.position, player) <= Companion.FAR_METERS)
    }

    @Test
    fun afterAWorldJumpReappearsBesideThePlayerOutOfViewAndRunsIntoView() {
        val c = companionAt(0.0, 0.0, -1.0)
        val player = Vec3(0.0, 1.3, 0.0)
        c.step(dt, player, lookingAhead, ground)
        // ARCore shifts the world by 2.5 m and more: the player now seems 9 m away.
        val jumped = Vec3(9.0, 1.3, 0.0)
        c.step(dt, jumped, lookingAhead, ground)
        assertEquals(2.0, horizontalDistance(c.position, jumped), 0.2)
        val toCompanion = Vec3(c.position.x - jumped.x, 0.0, c.position.z - jumped.z)
        val angle = Math.toDegrees(kotlin.math.acos((toCompanion dot lookingAhead) / toCompanion.norm()))
        assertTrue("should start out of view, at $angle deg", angle > 60)
        // ...then comes into view in front of the player.
        repeat(90) { c.step(dt, jumped, lookingAhead, ground) } // 3 s
        assertTrue("still behind the player", c.position.z < jumped.z)
    }

    @Test
    fun idleCompanionTurnsToFaceThePlayer() {
        val c = companionAt(2.0, 0.0, 0.0) // facing +Z; player is at -X
        repeat(45) { c.step(dt, Vec3(0.0, 1.3, 0.0), lookingAhead, ground) } // 1.5 s, before wandering
        assertEquals(Companion.Gait.IDLE, c.gait)
        val towardsPlayer = atan2(-1.0, 0.0)
        assertEquals(towardsPlayer, c.yaw, 0.01)
    }

    @Test
    fun wandersToSpotsInFrontOfThePlayerWhenIdle() {
        for (seed in 1..20) {
            val c = companionAt(0.0, 0.0, -1.5, seed)
            val player = Vec3(0.0, 1.3, 0.0)
            var spot: Vec3? = null
            repeat(30 * 8) { // 8 s: longer than the longest idle spell (7 s)
                c.step(dt, player, lookingAhead, ground)
                if (spot == null) spot = c.target
            }
            val s = spot ?: error("seed $seed: never wandered")
            val d = horizontalDistance(s, player)
            assertTrue("seed $seed: spot $d m away", d in 1.2..2.2 + 1e-9)
            val angle = abs(atan2(s.x - player.x, -(s.z - player.z)))
            assertTrue("seed $seed: spot ${angle * 180 / PI} deg off where the player looks", angle <= 70 * PI / 180 + 1e-9)
        }
    }

    @Test
    fun turnsGraduallyNotInstantly() {
        val c = companionAt(0.0, 0.0, -2.0) // facing +Z, i.e. towards the player
        // Player far behind it (at -Z): it must turn round and run; check the turn rate.
        val before = c.yaw
        c.step(dt, Vec3(0.0, 1.3, -8.0), lookingAhead, ground)
        assertTrue(abs(c.yaw - before) <= Companion.TURN_RADIANS_PER_SECOND * dt + 1e-9)
    }

    @Test
    fun staysPutWithoutReason() {
        val c = companionAt(0.0, 0.0, -1.5, seed = 3)
        repeat(30) { c.step(dt, Vec3(0.0, 1.3, 0.0), lookingAhead, ground) } // 1 s < 3 s minimum idle
        assertEquals(Companion.Gait.IDLE, c.gait)
        assertNull(c.target)
        assertEquals(-1.5, c.position.z, 1e-9)
    }

    @Test
    fun floorFromPointsIgnoresTablesAndUsesTheLowestSurface() {
        val camera = 1.35
        val floor = List(30) { Vec3(it * 0.1, 0.0 + (it % 3) * 0.01, -1.0) }
        val table = List(40) { Vec3(it * 0.05, 0.75, -0.5) } // 0.6 m below the phone: excluded
        val sofa = List(10) { Vec3(it * 0.05, 0.45, -2.0) }
        val estimate = Floor.fromPoints(floor + table + sofa, camera)!!
        assertEquals(0.0, estimate, 0.02)
        assertNull(Floor.fromPoints(table, camera))
        assertEquals(0.05, Floor.assumed(camera), 1e-9)
    }
}

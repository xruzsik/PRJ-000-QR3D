package com.smvit.ar.companion

import com.smvit.ar.qr.Mat3
import com.smvit.ar.qr.RigidPose
import com.smvit.ar.qr.Vec3
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.exp
import kotlin.math.min
import kotlin.random.Random

/**
 * A character that has left its code and roams the floor with the player: it
 * follows when they walk away (galloping if far behind), and wanders to spots
 * in front of them when idle so it stays in view. All positions are in
 * ARCore's world frame (metres, +Y up); "forward" for the model is its +Z.
 */
class Companion(
    start: RigidPose,
    /** Model length when roaming; speeds scale with it so gaits look natural. */
    private val bodyLength: Double,
    /** Extra angle (radians) added to wander spots, so several companions spread out. */
    private val sideBias: Double = 0.0,
    private val random: Random = Random.Default,
) {
    enum class Gait { IDLE, WALK, RUN }

    var position: Vec3 = start.translation
        private set
    var yaw: Double = yawOf(start.rotation)
        private set
    var gait = Gait.IDLE
        private set
    var target: Vec3? = null
        private set

    private var idleSeconds = 0.0
    private var wanderAfterSeconds = nextWanderDelay()

    val pose get() = RigidPose(Mat3.rotation(UP, yaw), position)

    /**
     * Advances [dt] seconds. [player] is the camera's position, [playerForward]
     * where it looks, [groundY] the floor height.
     */
    fun step(dt: Double, player: Vec3, playerForward: Vec3, groundY: Double) {
        // Hop down from the code and stay on the (re-estimated) floor.
        position = Vec3(position.x, position.y + (groundY - position.y) * (1 - exp(-dt / SETTLE_SECONDS)), position.z)

        var playerOnFloor = Vec3(player.x, position.y, player.z)
        var distanceToPlayer = (playerOnFloor - position).norm()

        // Far beyond any walk: ARCore's world frame jumped (seen up to 2.5 m on
        // the A53) or the player outran it. Reappear just out of view beside the
        // player and run into view, rather than galloping back across the map.
        if (distanceToPlayer > LOST_METERS) {
            position = besidePlayer(Vec3(player.x, groundY, player.z), playerForward)
            playerOnFloor = Vec3(player.x, groundY, player.z)
            distanceToPlayer = (playerOnFloor - position).norm()
            yaw = atan2(playerOnFloor.x - position.x, playerOnFloor.z - position.z)
            target = pickSpot(playerOnFloor, playerForward)
        }

        // Re-plan if the player walked off and the old spot is no longer near them.
        target?.let { if ((it - playerOnFloor).norm() > FAR_METERS) target = null }
        if (target == null && (distanceToPlayer > FAR_METERS || (gait == Gait.IDLE && idleSeconds > wanderAfterSeconds))) {
            target = pickSpot(playerOnFloor, playerForward)
        }

        val goal = target
        if (goal == null) {
            gait = Gait.IDLE
            idleSeconds += dt
            if (distanceToPlayer > 1e-3) turnTowards(playerOnFloor - position, dt)
            return
        }

        val toGoal = Vec3(goal.x - position.x, 0.0, goal.z - position.z)
        val distance = toGoal.norm()
        if (distance < ARRIVE_METERS) {
            target = null
            gait = Gait.IDLE
            idleSeconds = 0.0
            wanderAfterSeconds = nextWanderDelay()
            return
        }
        gait = if (distance > RUN_BEYOND_METERS) Gait.RUN else Gait.WALK
        val speed = bodyLength * if (gait == Gait.RUN) RUN_LENGTHS_PER_SECOND else WALK_LENGTHS_PER_SECOND
        turnTowards(toGoal, dt)
        position += toGoal * (min(speed * dt, distance) / distance)
    }

    /** A spot 1.2-2.2 m out, within 70 degrees of where the player looks (plus [sideBias]). */
    private fun pickSpot(playerOnFloor: Vec3, playerForward: Vec3): Vec3 {
        var forward = Vec3(playerForward.x, 0.0, playerForward.z)
        if (forward.norm() < 0.2) forward = Vec3(position.x - playerOnFloor.x, 0.0, position.z - playerOnFloor.z)
        if (forward.norm() < 1e-6) forward = Vec3(0.0, 0.0, -1.0)
        val angle = random.nextDouble(-MAX_WANDER_ANGLE, MAX_WANDER_ANGLE) + sideBias
        val radius = random.nextDouble(1.2, 2.2)
        return playerOnFloor + Mat3.rotation(UP, angle) * forward.normalized() * radius
    }

    /** Just outside the camera's view: 2 m out, 100 degrees to the player's side ([sideBias] picks which). */
    private fun besidePlayer(playerOnFloor: Vec3, playerForward: Vec3): Vec3 {
        var forward = Vec3(playerForward.x, 0.0, playerForward.z)
        if (forward.norm() < 0.2) forward = Vec3(0.0, 0.0, -1.0)
        val side = if (sideBias < 0) -1.0 else 1.0
        return playerOnFloor + Mat3.rotation(UP, side * REAPPEAR_ANGLE) * forward.normalized() * REAPPEAR_METERS
    }

    private fun turnTowards(direction: Vec3, dt: Double) {
        val desired = atan2(direction.x, direction.z)
        var diff = desired - yaw
        while (diff > PI) diff -= 2 * PI
        while (diff < -PI) diff += 2 * PI
        val maxTurn = TURN_RADIANS_PER_SECOND * dt
        yaw += if (abs(diff) <= maxTurn) diff else maxTurn * Math.signum(diff)
    }

    private fun nextWanderDelay() = random.nextDouble(3.0, 7.0)

    companion object {
        private val UP = Vec3(0.0, 1.0, 0.0)

        /** Beyond this the companion comes after the player. */
        const val FAR_METERS = 3.0
        /** Beyond this it is moved next to the player instead of walking back. */
        const val LOST_METERS = 6.0
        private const val REAPPEAR_METERS = 2.0
        private const val REAPPEAR_ANGLE = 100 * PI / 180
        /** Further than this from its goal it gallops, else it walks. */
        const val RUN_BEYOND_METERS = 1.5
        const val ARRIVE_METERS = 0.15
        const val WALK_LENGTHS_PER_SECOND = 1.0
        const val RUN_LENGTHS_PER_SECOND = 3.0
        const val TURN_RADIANS_PER_SECOND = PI
        const val SETTLE_SECONDS = 0.2
        private const val MAX_WANDER_ANGLE = 70 * PI / 180

        /** Heading of a pose's +Z, as an angle about +Y (0 = facing world +Z). */
        fun yawOf(rotation: Mat3): Double {
            val forward = rotation.column(2)
            return atan2(forward.x, forward.z)
        }
    }
}

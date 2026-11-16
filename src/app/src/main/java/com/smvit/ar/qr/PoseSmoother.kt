package com.smvit.ar.qr

import kotlin.math.exp

/**
 * Eases a displayed pose towards the latest target every frame, so a model
 * glides between QR measurements (which arrive a few times a second, each with
 * a little noise) instead of stepping. Frame-rate independent: after
 * [timeConstantNanos] it has covered ~63% of the way to a fixed target.
 */
class PoseSmoother(private val timeConstantNanos: Long = 80_000_000) {
    private var position: Vec3? = null
    private var rotation: Quat? = null
    private var lastNanos = 0L

    /** Advances to [nowNanos] towards [target] and returns the pose to display. */
    fun step(target: RigidPose, nowNanos: Long): RigidPose {
        val targetRotation = Quat.fromMatrix(target.rotation)
        val p = position
        val r = rotation
        if (p == null || r == null) {
            position = target.translation
            rotation = targetRotation
        } else {
            val dt = (nowNanos - lastNanos).coerceAtLeast(0)
            val t = 1 - exp(-dt.toDouble() / timeConstantNanos)
            position = p + (target.translation - p) * t
            rotation = r.nlerp(targetRotation, t)
        }
        lastNanos = nowNanos
        return RigidPose(rotation!!.toMatrix(), position!!)
    }
}

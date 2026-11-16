package com.smvit.ar.qr

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

// Small, dependency-free 3D maths so the QR pose code can be unit-tested on the JVM.

data class Vec3(val x: Double, val y: Double, val z: Double) {
    operator fun plus(o: Vec3) = Vec3(x + o.x, y + o.y, z + o.z)
    operator fun minus(o: Vec3) = Vec3(x - o.x, y - o.y, z - o.z)
    operator fun times(s: Double) = Vec3(x * s, y * s, z * s)
    operator fun div(s: Double) = Vec3(x / s, y / s, z / s)
    infix fun dot(o: Vec3) = x * o.x + y * o.y + z * o.z
    infix fun cross(o: Vec3) = Vec3(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x)
    fun norm() = sqrt(this dot this)
    fun normalized() = this / norm()

    companion object {
        val ZERO = Vec3(0.0, 0.0, 0.0)
    }
}

/** 3x3 matrix, row-major. */
class Mat3(private val m: DoubleArray) {
    init {
        require(m.size == 9)
    }

    operator fun get(row: Int, col: Int) = m[row * 3 + col]

    fun column(c: Int) = Vec3(this[0, c], this[1, c], this[2, c])

    operator fun times(v: Vec3) = Vec3(
        this[0, 0] * v.x + this[0, 1] * v.y + this[0, 2] * v.z,
        this[1, 0] * v.x + this[1, 1] * v.y + this[1, 2] * v.z,
        this[2, 0] * v.x + this[2, 1] * v.y + this[2, 2] * v.z,
    )

    operator fun times(o: Mat3) = Mat3(DoubleArray(9) { i ->
        val r = i / 3
        val c = i % 3
        this[r, 0] * o[0, c] + this[r, 1] * o[1, c] + this[r, 2] * o[2, c]
    })

    fun transpose() = Mat3(DoubleArray(9) { i -> this[i % 3, i / 3] })

    companion object {
        val IDENTITY = Mat3(doubleArrayOf(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0))

        fun fromColumns(a: Vec3, b: Vec3, c: Vec3) =
            Mat3(doubleArrayOf(a.x, b.x, c.x, a.y, b.y, c.y, a.z, b.z, c.z))

        fun fromRows(a: Vec3, b: Vec3, c: Vec3) =
            Mat3(doubleArrayOf(a.x, a.y, a.z, b.x, b.y, b.z, c.x, c.y, c.z))

        /** Rotation by [angle] radians about the unit vector [axis] (Rodrigues). */
        fun rotation(axis: Vec3, angle: Double): Mat3 {
            val (x, y, z) = axis.normalized()
            val c = cos(angle)
            val s = sin(angle)
            val t = 1 - c
            return Mat3(doubleArrayOf(
                t * x * x + c, t * x * y - s * z, t * x * z + s * y,
                t * x * y + s * z, t * y * y + c, t * y * z - s * x,
                t * x * z - s * y, t * y * z + s * x, t * z * z + c,
            ))
        }
    }
}

/** Unit quaternion (x, y, z, w), the same component order ARCore's Pose uses. */
data class Quat(val x: Double, val y: Double, val z: Double, val w: Double) {
    infix fun dot(o: Quat) = x * o.x + y * o.y + z * o.z + w * o.w
    operator fun unaryMinus() = Quat(-x, -y, -z, -w)
    operator fun plus(o: Quat) = Quat(x + o.x, y + o.y, z + o.z, w + o.w)

    fun normalized(): Quat {
        val n = sqrt(this dot this)
        return Quat(x / n, y / n, z / n, w / n)
    }

    fun toMatrix(): Mat3 = Mat3(doubleArrayOf(
        1 - 2 * (y * y + z * z), 2 * (x * y - z * w), 2 * (x * z + y * w),
        2 * (x * y + z * w), 1 - 2 * (x * x + z * z), 2 * (y * z - x * w),
        2 * (x * z - y * w), 2 * (y * z + x * w), 1 - 2 * (x * x + y * y),
    ))

    /** Angle in radians of the rotation taking this orientation to [o]. */
    fun angleTo(o: Quat) = 2 * acos(min(1.0, abs(this dot o)))

    /**
     * Normalised linear interpolation towards [o] along the shorter arc; [t] in
     * [0, 1]. Close enough to slerp for the small per-frame steps it's used for.
     */
    fun nlerp(o: Quat, t: Double): Quat {
        val target = if (this dot o < 0) -o else o
        return Quat(
            x + (target.x - x) * t,
            y + (target.y - y) * t,
            z + (target.z - z) * t,
            w + (target.w - w) * t,
        ).normalized()
    }

    companion object {
        /** Shepperd's method; [r] must be a rotation matrix. */
        fun fromMatrix(r: Mat3): Quat {
            val trace = r[0, 0] + r[1, 1] + r[2, 2]
            val q = when {
                trace > 0 -> {
                    val s = sqrt(trace + 1) * 2
                    Quat((r[2, 1] - r[1, 2]) / s, (r[0, 2] - r[2, 0]) / s, (r[1, 0] - r[0, 1]) / s, s / 4)
                }
                r[0, 0] > r[1, 1] && r[0, 0] > r[2, 2] -> {
                    val s = sqrt(max(0.0, 1 + r[0, 0] - r[1, 1] - r[2, 2])) * 2
                    Quat(s / 4, (r[0, 1] + r[1, 0]) / s, (r[0, 2] + r[2, 0]) / s, (r[2, 1] - r[1, 2]) / s)
                }
                r[1, 1] > r[2, 2] -> {
                    val s = sqrt(max(0.0, 1 + r[1, 1] - r[0, 0] - r[2, 2])) * 2
                    Quat((r[0, 1] + r[1, 0]) / s, s / 4, (r[1, 2] + r[2, 1]) / s, (r[0, 2] - r[2, 0]) / s)
                }
                else -> {
                    val s = sqrt(max(0.0, 1 + r[2, 2] - r[0, 0] - r[1, 1])) * 2
                    Quat((r[0, 2] + r[2, 0]) / s, (r[1, 2] + r[2, 1]) / s, s / 4, (r[1, 0] - r[0, 1]) / s)
                }
            }
            return q.normalized()
        }
    }
}

/**
 * Rigid transform. Named `aFromB`, it maps coordinates in frame B to frame A:
 * `aFromB.apply(pInB) = pInA`, and `aFromB compose bFromC = aFromC`.
 */
data class RigidPose(val rotation: Mat3, val translation: Vec3) {
    fun apply(p: Vec3) = rotation * p + translation

    infix fun compose(o: RigidPose) = RigidPose(rotation * o.rotation, rotation * o.translation + translation)

    companion object {
        val IDENTITY = RigidPose(Mat3.IDENTITY, Vec3.ZERO)
    }
}

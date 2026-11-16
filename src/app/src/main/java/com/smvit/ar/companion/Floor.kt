package com.smvit.ar.companion

import com.smvit.ar.qr.Vec3

/**
 * Where the floor is, without ARCore plane finding (which made tracking pause
 * several times a second on the A53): from tracked feature points well below
 * the phone, else assuming the phone is held at about chest height.
 */
object Floor {
    /** Typical height of a phone held while standing, used until points show up. */
    const val ASSUMED_PHONE_HEIGHT = 1.3

    /** Points closer below the phone than this are tables, chairs or hands, not the floor. */
    private const val MIN_DROP = 0.7
    private const val MAX_DROP = 2.2
    private const val MIN_POINTS = 12

    /**
     * Floor height from feature points, or null if too few lie in the plausible
     * band below the camera. Uses the lower third rather than the median, since
     * the floor is the lowest surface and low furniture can add points above it.
     */
    fun fromPoints(points: List<Vec3>, cameraY: Double): Double? {
        val heights = points.map { it.y }.filter { it in cameraY - MAX_DROP..cameraY - MIN_DROP }.sorted()
        if (heights.size < MIN_POINTS) return null
        return heights[heights.size / 3]
    }

    fun assumed(cameraY: Double) = cameraY - ASSUMED_PHONE_HEIGHT
}

package com.smvit.ar.qr

/**
 * Collects per-frame world poses of each QR payload and reports a pose only once
 * several recent sightings agree, so a single bad detection can't misplace a model.
 */
class QrPoseFilter(
    private val requiredSamples: Int = 5,
    private val maxAgeNanos: Long = 2_000_000_000,
    private val maxSpreadMeters: Double = 0.02,
    // Single square-on frames can be ~5 deg off in tilt (see QrPoseSolverTest),
    // so allow some room before calling samples inconsistent.
    private val maxSpreadRadians: Double = Math.toRadians(8.0),
    private val maxReprojectionErrorPx: Double = 3.0,
    /** Called with the worst sample's distance (m) and angle (rad) from the mean when samples disagree. */
    private val onInconsistent: (payload: String, spreadMeters: Double, spreadRadians: Double) -> Unit = { _, _, _ -> },
) {
    sealed interface Result {
        /** Sample rejected outright (poor fit to the detected corners). */
        data object Rejected : Result

        /** Waiting for more agreeing samples; [count] of [required] so far. */
        data class Collecting(val count: Int, val required: Int) : Result

        data class Stable(val worldFromAnchor: RigidPose) : Result
    }

    private class Sample(val timestampNanos: Long, val translation: Vec3, val rotation: Quat)

    private val samples = mutableMapOf<String, ArrayDeque<Sample>>()

    fun add(payload: String, worldFromAnchor: RigidPose, reprojectionErrorPx: Double, timestampNanos: Long): Result {
        if (reprojectionErrorPx > maxReprojectionErrorPx) return Result.Rejected

        val queue = samples.getOrPut(payload) { ArrayDeque() }
        queue.addLast(Sample(timestampNanos, worldFromAnchor.translation, Quat.fromMatrix(worldFromAnchor.rotation)))
        while (queue.isNotEmpty() && timestampNanos - queue.first().timestampNanos > maxAgeNanos) queue.removeFirst()
        while (queue.size > requiredSamples) queue.removeFirst()
        if (queue.size < requiredSamples) return Result.Collecting(queue.size, requiredSamples)

        val meanTranslation = queue.fold(Vec3.ZERO) { acc, s -> acc + s.translation } / queue.size.toDouble()
        // Quaternions q and -q are the same rotation: align signs before averaging.
        val reference = queue.first().rotation
        val meanRotation = queue
            .map { if (it.rotation dot reference < 0) -it.rotation else it.rotation }
            .reduce { acc, q -> acc + q }
            .normalized()

        val spreadMeters = queue.maxOf { (it.translation - meanTranslation).norm() }
        val spreadRadians = queue.maxOf { it.rotation.angleTo(meanRotation) }
        if (spreadMeters > maxSpreadMeters || spreadRadians > maxSpreadRadians) {
            onInconsistent(payload, spreadMeters, spreadRadians)
            queue.removeFirst()
            return Result.Collecting(queue.size, requiredSamples)
        }
        queue.clear()
        return Result.Stable(RigidPose(meanRotation.toMatrix(), meanTranslation))
    }

    fun reset() = samples.clear()
}

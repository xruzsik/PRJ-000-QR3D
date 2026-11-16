package com.smvit.ar.qr

import kotlin.math.abs

/**
 * Works out which detected corner is the code's real bottom-right one.
 *
 * ML Kit lists the corners clockwise, but starting from the corner nearest the
 * top-left of the *image*, not of the code, so a code seen sideways comes out
 * rotated by 90 degrees. The code itself says which way up it is: finder
 * patterns sit in its top-left, top-right and bottom-left corners only. Walking
 * the diagonal inwards from a finder corner crosses dark-light-dark-light-dark
 * runs in proportions 1:1:3:1:1, whatever the code's version or size.
 */
object QrOrientation {

    private const val SAMPLES = 400
    private val FINDER_RUNS = doubleArrayOf(1.0, 1.0, 3.0, 1.0, 1.0)

    /**
     * Index (into [QrPoseSolver.qrCorners] order) of the corner without a finder
     * pattern, or null unless exactly one corner lacks one.
     *
     * [luminance] samples the image at a point on the code, given in the QR frame
     * of the current corner labelling (metres; brighter = higher); it returns
     * null for points outside the image.
     */
    fun cornerWithoutFinder(sizeMeters: Double, luminance: (x: Double, y: Double) -> Double?): Int? =
        analyze(sizeMeters, luminance)?.cornerWithoutFinder

    /**
     * [cornerWithoutFinder] plus, for each corner whose finder was confirmed, how
     * many modules span the detected corners and how far (in modules) the detected
     * corner lies inside the code's true corner (negative: outside). Detected
     * corners inside the true outline make the code look smaller, so the solver
     * places it too far away.
     */
    data class Analysis(
        val cornerWithoutFinder: Int?,
        val modulesAcross: List<Double>,
        val cornerInsetModules: List<Double>,
    )

    fun analyze(sizeMeters: Double, luminance: (x: Double, y: Double) -> Double?): Analysis? {
        val corners = QrPoseSolver.qrCorners(sizeMeters)
        // Corner to centre is N/2 modules along the diagonal; the finder plus its
        // separator is 8 of them, i.e. 16/N of the way: 0.76 for the smallest
        // (21-module) code. Only the first runs are used, so going further is harmless.
        val profiles = corners.map { (cx, cy) ->
            DoubleArray(SAMPLES) { i ->
                val s = 0.8 * i / (SAMPLES - 1)
                luminance(cx * (1 - s), cy * (1 - s)) ?: return null
            }
        }
        val all = profiles.flatMap { it.asList() }
        val threshold = (all.min() + all.max()) / 2
        if (all.max() - all.min() < 1e-6) return null

        val h = sizeMeters / 2
        val modulesAcross = mutableListOf<Double>()
        val cornerInsetModules = mutableListOf<Double>()
        val matches = profiles.map { diagonalMatch(it, threshold) }
        val finders = corners.mapIndexed { i, (cx, cy) ->
            val match = matches[i] ?: return@mapIndexed false
            // The bottom-right alignment pattern can mimic the diagonal runs when
            // the data before it happens to fit (1 in 16), so also require the
            // finder's outer ring: 7 dark modules along both edges, then light.
            val module = match.sPerModule * h
            val origin = Vec3(cx * (1 - match.cornerS), cy * (1 - match.cornerS), 0.0)
            val inX = Vec3(-Math.signum(cx), 0.0, 0.0)
            val inY = Vec3(0.0, -Math.signum(cy), 0.0)
            val confirmed = edgeIsFinderRing(origin, inX, inY, module, threshold, luminance) &&
                edgeIsFinderRing(origin, inY, inX, module, threshold, luminance)
            if (confirmed) {
                // Corner to centre is s = 1 and N/2 modules along the diagonal.
                modulesAcross += 2 / match.sPerModule
                cornerInsetModules += -match.cornerS / match.sPerModule
            }
            confirmed
        }
        val strict = finders.withIndex().singleOrNull { !it.value }?.index
        // Viewed at an angle or with motion blur, a real finder's edge ring often
        // fails, leaving the strict check undecided. Then settle for the diagonal
        // runs alone when exactly one corner lacks them. That can be wrong only
        // if blur breaks a real finder's diagonal while the bottom-right corner
        // happens to mimic one; a wrong labelling is 90+ degrees off and won't
        // match the other sightings a lock-on needs (QrPoseFilter).
        val lenient = matches.withIndex().singleOrNull { it.value == null }?.index
        return Analysis(strict ?: lenient, modulesAcross, cornerInsetModules)
    }

    /** Samples module centres along [along], half a module in along [across]. */
    private fun edgeIsFinderRing(
        origin: Vec3,
        along: Vec3,
        across: Vec3,
        module: Double,
        threshold: Double,
        luminance: (x: Double, y: Double) -> Double?,
    ): Boolean = (0..7).all { k ->
        val p = origin + along * ((k + 0.5) * module) + across * (0.5 * module)
        val v = luminance(p.x, p.y) ?: return false
        (v < threshold) == (k < 7)
    }

    /**
     * For a code already being tracked: index of the candidate orientation (one per
     * corner labelling) closest to the [expected] one, or null if none is within
     * [maxAngleRad]. The four labellings are 90 degrees apart, so this needs no
     * look at the finder patterns, which motion blur often makes unreadable.
     */
    fun closestToExpected(candidates: List<Mat3?>, expected: Mat3, maxAngleRad: Double = Math.toRadians(45.0)): Int? {
        val reference = Quat.fromMatrix(expected)
        return candidates.withIndex()
            .mapNotNull { (i, r) -> r?.let { i to Quat.fromMatrix(it).angleTo(reference) } }
            .filter { it.second <= maxAngleRad }
            .minByOrNull { it.second }
            ?.first
    }

    /** Corners reordered so that index 2 (bottom-right) is [cornerWithoutFinder]'s result. */
    fun <T> relabel(corners: List<T>, withoutFinder: Int): List<T> =
        List(4) { i -> corners[(withoutFinder - 2 + i + 4) % 4] }

    /**
     * Where the code's true corner is along the diagonal profile (negative: outside
     * the detected corner), and its module size, both in s units.
     */
    private class DiagonalMatch(val cornerS: Double, val sPerModule: Double)

    private fun diagonalMatch(profile: DoubleArray, threshold: Double): DiagonalMatch? {
        val sPerSample = 0.8 / (SAMPLES - 1)
        // Run lengths of dark/light from the corner inwards.
        val runs = mutableListOf<Pair<Boolean, Int>>()
        for (v in profile) {
            val dark = v < threshold
            if (runs.isNotEmpty() && runs.last().first == dark) {
                runs[runs.lastIndex] = dark to runs.last().second + 1
            } else {
                runs += dark to 1
            }
        }
        // Pose error can put the first few samples on the white margin outside.
        var skipped = 0
        if (runs.isNotEmpty() && !runs[0].first && runs[0].second < SAMPLES / 20) skipped = runs.removeAt(0).second
        if (runs.size < 6) return null
        // dark, light, dark, light, dark, then the light separator.
        if (!runs[0].first || runs[5].first) return null

        val lengths = runs.take(5).map { it.second.toDouble() }
        // Module size from the inner runs only (1:3:1:1): the first dark run is cut
        // short or stretched when the detected corner is off the true one.
        val unit = (1..4).sumOf { lengths[it] } / (1..4).sumOf { FINDER_RUNS[it] }
        if (!(1..4).all { abs(lengths[it] - FINDER_RUNS[it] * unit) <= 0.7 * unit }) return null
        if (lengths[0] !in 0.3 * unit..1.7 * unit) return null
        // The first dark run should be exactly one module, ending where run 1 starts.
        val cornerSample = skipped + lengths[0] - unit
        return DiagonalMatch(cornerS = cornerSample * sPerSample, sPerModule = unit * sPerSample)
    }
}

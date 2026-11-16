package com.smvit.ar.qr

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.floor
import kotlin.random.Random

class QrOrientationTest {

    private val size = 0.10

    /**
     * QR-like module grid (row 0 at the top, true = dark): finders with white
     * separators in three corners, timing lines, the bottom-right alignment
     * pattern from version 2 on, and random data everywhere else.
     */
    private fun grid(n: Int, random: Random): Array<BooleanArray> {
        val g = Array(n) { BooleanArray(n) { random.nextBoolean() } }
        fun finder(top: Int, left: Int) {
            for (r in -1..7) for (c in -1..7) {
                val rr = top + r
                val cc = left + c
                if (rr !in 0 until n || cc !in 0 until n) continue
                val ring = maxOf(kotlin.math.abs(r - 3), kotlin.math.abs(c - 3))
                g[rr][cc] = ring != 2 && ring != 4 // separator ring (4) and inner ring (2) light
            }
        }
        finder(0, 0)
        finder(0, n - 7)
        finder(n - 7, 0)
        for (i in 8 until n - 8) {
            g[6][i] = i % 2 == 0
            g[i][6] = i % 2 == 0
        }
        if (n > 21) {
            val a = n - 7
            for (r in -2..2) for (c in -2..2) g[a + r][a + c] = maxOf(kotlin.math.abs(r), kotlin.math.abs(c)) != 1
        }
        return g
    }

    /**
     * Luminance in a labelling turned [quarterTurns] x 90 degrees clockwise from
     * the code's true orientation (what ML Kit does for a code seen sideways).
     */
    private fun sampler(g: Array<BooleanArray>, quarterTurns: Int): (Double, Double) -> Double? = { x0, y0 ->
        var x = x0
        var y = y0
        repeat(quarterTurns) { val t = x; x = y; y = -t } // clockwise quarter turn
        val n = g.size
        val h = size / 2
        val col = floor((x + h) / size * n).toInt()
        val row = floor((h - y) / size * n).toInt()
        if (row in 0 until n && col in 0 until n && g[row][col]) 0.1 else 0.9
    }

    @Test
    fun findsBottomRightForEveryLabellingSizeAndData() {
        var undecided = 0
        val undecidedCases = mutableListOf<String>()
        var total = 0
        for (n in listOf(21, 25, 29, 33, 41)) for (seed in 0 until 100) for (turns in 0..3) {
            val g = grid(n, Random(seed * 31 + n))
            val result = QrOrientation.cornerWithoutFinder(size, sampler(g, turns))
            total++
            if (result == null) {
                undecided++
                undecidedCases += "n=$n turns=$turns"
                continue
            }
            assertEquals("n=$n seed=$seed turns=$turns", (2 - turns + 4) % 4, result)
        }
        // Undecided sightings are skipped, which only costs a frame.
        assertTrue("undecided $undecided of $total: ${undecidedCases.groupingBy { it }.eachCount()}", undecided <= total / 50)
    }

    @Test
    fun relabelPutsTheFinderlessCornerBottomRight() {
        val corners = listOf("a", "b", "c", "d")
        assertEquals(corners, QrOrientation.relabel(corners, 2))
        assertEquals(listOf("b", "c", "d", "a"), QrOrientation.relabel(corners, 3))
        assertEquals(listOf("d", "a", "b", "c"), QrOrientation.relabel(corners, 1))
        assertEquals(listOf("c", "d", "a", "b"), QrOrientation.relabel(corners, 0))
    }

    @Test
    fun toleratesCornersLyingSlightlyOutsideTheCode() {
        // Pose error of a third of a module: samples start on the white margin.
        val g = grid(25, Random(7))
        val inset = sampler(g, 0)
        val grown = 1 + (1.0 / 3) / 25 * 2
        val result = QrOrientation.cornerWithoutFinder(size) { x, y -> inset(x * grown, y * grown) }
        assertEquals(2, result)
    }

    @Test
    fun estimatesModulesAcrossAndExposesInsetCorners() {
        val g = grid(25, Random(3))
        val exact = QrOrientation.analyze(size, sampler(g, 0))!!
        assertTrue("$exact", exact.modulesAcross.size == 3 && exact.modulesAcross.all { it in 24.8..25.2 })
        assertTrue("$exact", exact.cornerInsetModules.all { it in -0.1..0.1 })

        // Corners detected half a module inside the true outline on every side:
        // the code spans 24 modules between them.
        val shrink = 24.0 / 25
        val inset = QrOrientation.analyze(size) { x, y -> sampler(g, 0)(x * shrink, y * shrink) }!!
        assertTrue("$inset", inset.modulesAcross.size == 3 && inset.modulesAcross.all { it in 23.8..24.2 })
        assertTrue("$inset", inset.cornerInsetModules.all { it in 0.4..0.6 })
        assertEquals(2, inset.cornerWithoutFinder)
    }

    @Test
    fun picksTheLabellingClosestToTheTrackedOrientation() {
        // Candidates: the code's anchor rotated by each quarter turn about its
        // normal (+Y), as the four corner labellings produce.
        val tracked = Mat3.rotation(Vec3(0.3, 1.0, 0.1), 0.8)
        val candidates = (0..3).map { k -> tracked * Mat3.rotation(Vec3(0.0, 1.0, 0.0), k * kotlin.math.PI / 2) }
        // Each candidate measured a few degrees off (noise, slight motion).
        val noisy = candidates.map { it * Mat3.rotation(Vec3(1.0, 0.0, 0.0), Math.toRadians(4.0)) }
        for (k in 0..3) {
            val rotated = noisy.indices.map { noisy[(it + k) % 4] }
            assertEquals((4 - k) % 4, QrOrientation.closestToExpected(rotated, tracked))
        }
        // A failed solve for some labelling is skipped.
        assertEquals(0, QrOrientation.closestToExpected(listOf(noisy[0], null, noisy[2], null), tracked))
        // Nothing near the tracked orientation (e.g. the code was flipped over).
        val flipped = candidates.map { it * Mat3.rotation(Vec3(1.0, 0.0, 0.0), kotlin.math.PI) }
        assertEquals(null, QrOrientation.closestToExpected(flipped, tracked))
    }

    @Test
    fun decidesFromDiagonalsWhenAFindersEdgeRingIsUnreadable() {
        // Blur wiping out part of the top-left finder's top edge: its diagonal
        // still reads 1:1:3:1:1 but its edge ring doesn't, so the strict check
        // sees two corners without a finder.
        for (turns in 0..3) {
            val g = grid(25, Random(11))
            for (c in 2..6) g[0][c] = false
            assertEquals("turns=$turns", (2 - turns + 4) % 4, QrOrientation.cornerWithoutFinder(size, sampler(g, turns)))
        }
    }

    @Test
    fun undecidedWhenImageIsBlank() {
        assertEquals(null, QrOrientation.cornerWithoutFinder(size) { _, _ -> 0.5 })
        assertEquals(null, QrOrientation.cornerWithoutFinder(size) { _, _ -> null })
    }
}

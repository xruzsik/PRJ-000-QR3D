package com.smvit.ar.qr

import org.junit.Assert.assertEquals
import org.junit.Test

class ScanPacerTest {
    private val s = 1_000_000_000L

    @Test
    fun slowUntilACodeIsSeenThenFastForThreeSeconds() {
        val p = ScanPacer()
        assertEquals(p.slowNanos, p.intervalNanos(10 * s, followingACode = false))
        p.onKnownSighting(10 * s)
        assertEquals(p.fastNanos, p.intervalNanos(12 * s, followingACode = false))
        assertEquals(p.slowNanos, p.intervalNanos(13 * s, followingACode = false))
    }

    @Test
    fun halfAsOftenWhenHot() {
        val p = ScanPacer()
        assertEquals(2 * p.slowNanos, p.intervalNanos(10 * s, followingACode = false, hot = true))
        assertEquals(2 * p.fastNanos, p.intervalNanos(10 * s, followingACode = true, hot = true))
    }

    @Test
    fun alwaysFastWhileACharacterStandsOnItsCode() {
        val p = ScanPacer()
        assertEquals(p.fastNanos, p.intervalNanos(100 * s, followingACode = true))
    }
}

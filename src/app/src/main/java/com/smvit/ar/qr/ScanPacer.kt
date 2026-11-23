package com.smvit.ar.qr

/**
 * How often to run QR detection (NFR-07: battery and heat). Fast while a
 * character stands on a code that's being followed, or a known code was seen
 * recently (so a new one locks on quickly); slow otherwise, e.g. while walking
 * between stops with a companion. Both intervals double while the phone is hot.
 */
class ScanPacer(
    val fastNanos: Long = 125_000_000L,
    val slowNanos: Long = 500_000_000L,
    /** How long to stay fast after the last sighting of a known code. */
    private val holdFastNanos: Long = 3_000_000_000L,
) {
    private var lastKnownSightingNanos: Long? = null

    fun onKnownSighting(nowNanos: Long) {
        lastKnownSightingNanos = nowNanos
    }

    /** [followingACode]: a character is shown on its code (not roaming); [hot]: the phone is overheating. */
    fun intervalNanos(nowNanos: Long, followingACode: Boolean, hot: Boolean = false): Long {
        val last = lastKnownSightingNanos
        val recent = last != null && nowNanos - last < holdFastNanos
        val interval = if (followingACode || recent) fastNanos else slowNanos
        return if (hot) interval * 2 else interval
    }
}

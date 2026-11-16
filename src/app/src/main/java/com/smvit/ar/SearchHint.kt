package com.smvit.ar

/**
 * Decides when to show a tip because nothing has been found for a while
 * (US-07): after [afterNanos] of searching without seeing a known code.
 */
class SearchHint(private val afterNanos: Long = 5_000_000_000L) {
    enum class Hint {
        /** AR tracking hasn't started: the camera needs to see more of the surroundings. */
        MOVE_SLOWLY,

        /** Tracking works but no known code is being read. */
        GET_CLOSER,
    }

    private var searchingSinceNanos = 0L

    /** Restart the wait, e.g. on "scan again" or when a known code is sighted. */
    fun reset() {
        searchingSinceNanos = 0L
    }

    /**
     * Call every frame. [searching]: no character is shown yet; [readyToScan]:
     * AR tracking is warmed up and codes are being read.
     */
    fun update(nowNanos: Long, searching: Boolean, readyToScan: Boolean): Hint? {
        if (!searching || searchingSinceNanos == 0L) {
            searchingSinceNanos = nowNanos
            return null
        }
        if (nowNanos - searchingSinceNanos < afterNanos) return null
        return if (readyToScan) Hint.GET_CLOSER else Hint.MOVE_SLOWLY
    }
}

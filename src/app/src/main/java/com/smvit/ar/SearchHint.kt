package com.smvit.ar

/**
 * Decides which tip to show (US-07):
 * - when ARCore has stopped tracking for [failureAfterNanos], the reason it
 *   gives (too dark, too fast, nothing to see), even while a character is out;
 * - when nothing has been found for [searchAfterNanos], a general tip.
 */
class SearchHint(
    private val searchAfterNanos: Long = 5_000_000_000L,
    private val failureAfterNanos: Long = 2_000_000_000L,
) {
    /** Why ARCore isn't tracking (from its TrackingFailureReason). */
    enum class Failure { TOO_DARK, TOO_FAST, NOT_ENOUGH_DETAIL, OTHER }

    enum class Hint {
        TOO_DARK,
        TOO_FAST,
        NOT_ENOUGH_DETAIL,

        /** AR tracking hasn't started, for no particular reason given. */
        MOVE_SLOWLY,

        /** Tracking works but no known code is being read. */
        GET_CLOSER,
    }

    private var searchingSinceNanos = 0L
    private var failure: Failure? = null
    private var failingSinceNanos = 0L

    /** Restart the search wait, e.g. on "scan again" or when a known code is sighted. */
    fun reset() {
        searchingSinceNanos = 0L
    }

    /**
     * Call every frame. [searching]: no character is shown yet; [readyToScan]:
     * AR tracking is warmed up and codes are being read; [failure]: why ARCore
     * isn't tracking, or null while it is (or gives no reason).
     */
    fun update(nowNanos: Long, searching: Boolean, readyToScan: Boolean, failure: Failure? = null): Hint? {
        if (failure != this.failure) {
            this.failure = failure
            failingSinceNanos = nowNanos
        }
        if (failure != null && nowNanos - failingSinceNanos >= failureAfterNanos) {
            return when (failure) {
                Failure.TOO_DARK -> Hint.TOO_DARK
                Failure.TOO_FAST -> Hint.TOO_FAST
                Failure.NOT_ENOUGH_DETAIL -> Hint.NOT_ENOUGH_DETAIL
                Failure.OTHER -> Hint.MOVE_SLOWLY
            }
        }

        if (!searching || searchingSinceNanos == 0L) {
            searchingSinceNanos = nowNanos
            return null
        }
        if (nowNanos - searchingSinceNanos < searchAfterNanos) return null
        return if (readyToScan) Hint.GET_CLOSER else Hint.MOVE_SLOWLY
    }
}

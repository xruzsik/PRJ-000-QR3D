package com.smvit.ar

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SearchHintTest {
    private val s = 1_000_000_000L

    @Test
    fun quietForFiveSecondsThenTipDependsOnTracking() {
        val hint = SearchHint()
        assertNull(hint.update(10 * s, searching = true, readyToScan = false))
        assertNull(hint.update(14 * s, searching = true, readyToScan = false))
        assertEquals(SearchHint.Hint.MOVE_SLOWLY, hint.update(15 * s, searching = true, readyToScan = false))
        assertEquals(SearchHint.Hint.GET_CLOSER, hint.update(16 * s, searching = true, readyToScan = true))
    }

    @Test
    fun foundCharacterOrResetRestartsTheWait() {
        val hint = SearchHint()
        hint.update(0 + 1, searching = true, readyToScan = true)
        assertEquals(SearchHint.Hint.GET_CLOSER, hint.update(6 * s, searching = true, readyToScan = true))
        assertNull(hint.update(7 * s, searching = false, readyToScan = true))
        assertNull(hint.update(11 * s, searching = true, readyToScan = true))
        assertEquals(SearchHint.Hint.GET_CLOSER, hint.update(12 * s, searching = true, readyToScan = true))
        hint.reset()
        assertNull(hint.update(13 * s, searching = true, readyToScan = true))
        assertNull(hint.update(17 * s, searching = true, readyToScan = true))
    }
}

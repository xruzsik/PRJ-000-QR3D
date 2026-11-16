package com.smvit.ar

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TrailProgressTest {

    private val empty = TrailProgress(listOf("fox", "wolf", "deer"), emptySet())

    @Test
    fun badgeOnlyWhenTheLastCharacterIsFound() {
        val a = empty.find("fox")
        assertTrue(a.isNew)
        assertFalse(a.earnedBadge)
        assertEquals(1, a.progress.count)

        val b = a.progress.find("deer")
        assertFalse(b.earnedBadge)
        val c = b.progress.find("wolf")
        assertTrue(c.isNew)
        assertTrue(c.earnedBadge)
        assertTrue(c.progress.isComplete)
        assertEquals(3, c.progress.count)
    }

    @Test
    fun findingAgainOrOffTrailChangesNothing() {
        val once = empty.find("fox").progress
        val again = once.find("fox")
        assertFalse(again.isNew)
        assertFalse(again.earnedBadge)
        assertEquals(once, again.progress)

        val offTrail = once.find("axis_cube")
        assertFalse(offTrail.isNew)
        assertEquals(once, offTrail.progress)
    }

    @Test
    fun badgeIsEarnedOnlyOnce() {
        var p = empty
        for (id in empty.trail) p = p.find(id).progress
        assertTrue(p.isComplete)
        assertFalse(p.find("fox").earnedBadge)
    }

    @Test
    fun foundCharactersLaterRemovedFromTheTrailDontCount() {
        val p = TrailProgress(listOf("fox", "wolf"), setOf("fox", "old_character"))
        assertEquals(1, p.count)
        assertFalse(p.isComplete)
    }

    @Test
    fun catalogTrailIsValid() {
        assertTrue(Catalog.trail.isNotEmpty())
        assertEquals(Catalog.trail.size, Catalog.trail.toSet().size)
        for (id in Catalog.trail) {
            val entry = Catalog.byId(id)
            assertTrue("trail character $id missing from catalog", entry != null)
            assertTrue("trail character $id has no story", !entry!!.story.isNullOrBlank())
        }
    }
}

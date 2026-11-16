package com.smvit.ar

/** Which of a trail's characters have been found; the badge is earned when all are. */
data class TrailProgress(val trail: List<String>, val found: Set<String>) {
    val count get() = trail.count { it in found }
    val total get() = trail.size
    val isComplete get() = trail.isNotEmpty() && count == total

    /** Result of finding the character [id]. */
    data class Find(val progress: TrailProgress, val isNew: Boolean, val earnedBadge: Boolean)

    fun find(id: String): Find {
        if (id !in trail || id in found) return Find(this, isNew = false, earnedBadge = false)
        val next = copy(found = found + id)
        return Find(next, isNew = true, earnedBadge = next.isComplete)
    }
}

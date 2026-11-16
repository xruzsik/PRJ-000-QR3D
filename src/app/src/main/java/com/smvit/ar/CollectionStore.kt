package com.smvit.ar

import android.content.Context
import androidx.core.content.edit

/** The ids of characters found so far, kept on the phone (works offline, survives restarts). */
class CollectionStore(context: Context) {
    private val prefs = context.getSharedPreferences("collection", Context.MODE_PRIVATE)

    fun load(): Set<String> = prefs.getStringSet(KEY_FOUND, null)?.toSet().orEmpty()

    fun save(found: Set<String>) = prefs.edit { putStringSet(KEY_FOUND, found) }

    private companion object {
        const val KEY_FOUND = "found"
    }
}

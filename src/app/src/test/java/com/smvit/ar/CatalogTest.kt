package com.smvit.ar

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class CatalogTest {

    @Test
    fun everyModelFileExistsInAssets() {
        // Unit tests run with the module directory (app/) as working directory.
        for (entry in Catalog.entries) {
            val file = File("src/main/assets", entry.model)
            assertTrue("${entry.id}: missing ${file.path}", file.isFile)
            assertEquals("${entry.id}: not a glTF binary", "glTF", file.inputStream().use { String(it.readNBytes(4)) })
        }
    }

    @Test
    fun everyNamedAnimationExistsInItsModel() {
        for (entry in Catalog.entries) {
            val names = listOfNotNull(entry.animation, entry.walkAnimation, entry.runAnimation)
            if (names.isEmpty()) continue
            val bytes = File("src/main/assets", entry.model).readBytes()
            // GLB: 12-byte header, then the JSON chunk's length, type and text.
            val jsonLength = (bytes[12].toInt() and 0xFF) or ((bytes[13].toInt() and 0xFF) shl 8) or
                ((bytes[14].toInt() and 0xFF) shl 16) or ((bytes[15].toInt() and 0xFF) shl 24)
            val json = String(bytes, 20, jsonLength)
            val animations = json.substringAfter("\"animations\":[", "")
            for (animation in names) {
                assertTrue("${entry.id}: no animation \"$animation\" in ${entry.model}", "\"name\":\"$animation\"" in animations)
            }
        }
    }

    @Test
    fun printedTestCodesStandInForAnimals() {
        assertEquals("fox", Catalog.forPayload("smvit-ar:test_cube")?.id)
        assertEquals("deer", Catalog.forPayload("smvit-ar:test_cube_2")?.id)
    }

    @Test
    fun idsAreUniqueAndSizesSane() {
        assertEquals(Catalog.entries.size, Catalog.entries.map { it.id }.toSet().size)
        for (entry in Catalog.entries) {
            assertTrue(entry.id, entry.id.matches(Regex("[a-z0-9_]+")))
            assertTrue(entry.id, entry.qrSizeMeters in 0.02..0.5)
            assertTrue(entry.id, entry.modelSizeMeters in 0.01f..5f)
        }
    }

    @Test
    fun looksUpByFullPayloadOnly() {
        assertEquals("fox", Catalog.forPayload("smvit-ar:fox")?.id)
        assertEquals("axis_cube", Catalog.forPayload("smvit-ar:axis_cube")?.id)
        assertNull(Catalog.forPayload("fox"))
        assertNull(Catalog.forPayload("smvit-ar:nonexistent"))
        assertNull(Catalog.forPayload("https://example.com"))
    }
}

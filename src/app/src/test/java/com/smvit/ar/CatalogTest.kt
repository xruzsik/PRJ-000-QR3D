package com.smvit.ar

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.Assert.assertFalse
import java.io.File

/** The real route file, as the app reads it. */
val routeCatalog: Catalog by lazy { Catalog.fromJson(File("src/main/assets/route.json").readText()) }

class CatalogTest {
    private val catalog get() = routeCatalog

    @Test
    fun everyModelFileExistsInAssets() {
        // Unit tests run with the module directory (app/) as working directory.
        for (entry in catalog.entries) {
            val file = File("src/main/assets", entry.model)
            assertTrue("${entry.id}: missing ${file.path}", file.isFile)
            assertEquals("${entry.id}: not a glTF binary", "glTF", file.inputStream().use { String(it.readNBytes(4)) })
        }
    }

    @Test
    fun everyNamedAnimationExistsInItsModel() {
        for (entry in catalog.entries) {
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
        assertEquals("fox", catalog.forPayload("smvit-ar:test_cube")?.id)
        assertEquals("deer", catalog.forPayload("smvit-ar:test_cube_2")?.id)
    }

    @Test
    fun idsAreUniqueAndSizesSane() {
        assertEquals(catalog.entries.size, catalog.entries.map { it.id }.toSet().size)
        for (entry in catalog.entries) {
            assertTrue(entry.id, entry.id.matches(Regex("[a-z0-9_]+")))
            assertTrue(entry.id, entry.qrSizeMeters in 0.02..0.5)
            assertTrue(entry.id, entry.modelSizeMeters in 0.01f..5f)
        }
    }

    @Test
    fun storiesGivenAsSentenceListsAreJoined() {
        val fox = catalog.byId("fox")!!
        assertTrue(fox.story!!.startsWith("Líška hrdzavá"))
        assertTrue("sentences joined with one space", ". Má výborný sluch" in fox.story!!)
        assertEquals(0.20, fox.offset.translation.x, 1e-9)
        assertEquals("Gallop", fox.runAnimation)
    }

    @Test
    fun looksUpByFullPayloadOnly() {
        assertEquals("fox", catalog.forPayload("smvit-ar:fox")?.id)
        assertEquals("axis_cube", catalog.forPayload("smvit-ar:axis_cube")?.id)
        assertNull(catalog.forPayload("fox"))
        assertNull(catalog.forPayload("smvit-ar:nonexistent"))
        assertNull(catalog.forPayload("https://example.com"))
    }

    // ---- Errors a hand edit could cause: each should name the place. ----

    private fun errorFor(json: String): String =
        try {
            Catalog.fromJson(json)
            throw AssertionError("expected an error for: $json")
        } catch (e: Catalog.FormatException) {
            e.message!!
        }

    private val fox = """{"id":"fox","name":"Líška","model":"models/fox.glb","qrSizeMeters":0.1,"modelSizeMeters":0.3}"""

    @Test
    fun minimalRouteParses() {
        val c = Catalog.fromJson("""{"stops":["fox"],"characters":[$fox]}""")
        assertEquals(listOf("fox"), c.trail)
        assertNull(c.byId("fox")!!.story)
    }

    @Test
    fun syntaxErrorsSayLineAndColumn() {
        val message = errorFor("{\n  \"stops\": [\"fox\"]\n  \"characters\": []\n}")
        assertTrue(message, message.startsWith("route.json: line 3, column 3"))
        assertTrue(message, "missing comma" in message)
    }

    @Test
    fun badFieldsNameTheCharacter() {
        val typo = errorFor("""{"stops":["fox"],"characters":[${fox.replace("modelSizeMeters", "modelSize")}]}""")
        assertTrue(typo, "character \"fox\"" in typo && "unknown field \"modelSize\"" in typo)

        val quoted = errorFor("""{"stops":["fox"],"characters":[${fox.replace("0.1,", "\"0.1\",")}]}""")
        assertTrue(quoted, "\"qrSizeMeters\" must be a number" in quoted)

        val huge = errorFor("""{"stops":["fox"],"characters":[${fox.replace("0.3}", "30}")}]}""")
        assertTrue(huge, "\"modelSizeMeters\" is 30.0" in huge)

        val missing = errorFor("""{"stops":["fox"],"characters":[${fox.replace(",\"name\":\"Líška\"", "")}]}""")
        assertTrue(missing, "\"name\" is missing" in missing)
    }

    @Test
    fun stopsAndAliasesMustNameCharacters() {
        assertTrue("stop \"wolf\"" in errorFor("""{"stops":["fox","wolf"],"characters":[$fox]}"""))
        assertTrue("listed twice" in errorFor("""{"stops":["fox","fox"],"characters":[$fox]}"""))
        assertTrue("unknown character \"owl\"" in errorFor("""{"stops":["fox"],"aliases":{"x":"owl"},"characters":[$fox]}"""))
        assertTrue("defined twice" in errorFor("""{"stops":["fox"],"characters":[$fox,$fox]}"""))
        assertFalse(errorFor("""{"stops":[],"characters":[$fox]}""").isEmpty())
    }
}

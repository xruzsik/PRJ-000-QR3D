package com.smvit.ar

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonTest {
    @Test
    fun parsesAllValueKinds() {
        val v = Json.parse(""" { "a": [1, -2.5, 3e2], "b": "ľ\"\\\ná", "c": true, "d": false, "e": null, "f": {} } """)
        assertEquals(
            mapOf("a" to listOf(1.0, -2.5, 300.0), "b" to "ľ\"\\\ná", "c" to true, "d" to false, "e" to null, "f" to emptyMap<String, Any?>()),
            v,
        )
        assertNull(Json.parse("null"))
    }

    @Test
    fun keepsKeyOrder() {
        assertEquals(listOf("z", "a", "m"), (Json.parse("""{"z":1,"a":2,"m":3}""") as Map<*, *>).keys.toList())
    }

    private fun error(text: String) =
        try {
            Json.parse(text)
            throw AssertionError("no error for $text")
        } catch (e: Json.ParseException) {
            e.message!!
        }

    @Test
    fun rejectsCommonMistakes() {
        assertTrue(error("""{"a":1,}""").contains("expected a key"))          // trailing comma
        assertTrue(error("""[1 2]""").contains("missing comma"))
        assertTrue(error("""{"a":1,"a":2}""").contains("appears twice"))
        assertTrue(error("\"two\nlines\"").contains("line break inside a string"))
        assertTrue(error("""{"a":'x'}""").contains("unexpected character"))  // single quotes
        assertTrue(error("""{"a":1} x""").contains("after the end"))
        assertTrue(error("""{"a":""").contains("end of file"))
    }
}

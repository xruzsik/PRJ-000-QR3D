package com.smvit.ar

/**
 * A small JSON reader for the route file. Objects become [Map]s (keys in file
 * order), arrays [List]s, numbers [Double]s; plus [String], [Boolean] and null.
 *
 * Hand-written rather than Android's org.json so the route file can be read and
 * checked by plain JVM unit tests, without adding a library.
 */
object Json {
    class ParseException(message: String) : IllegalArgumentException(message)

    fun parse(text: String): Any? {
        val reader = Reader(text)
        reader.skipWhitespace()
        val value = reader.value()
        reader.skipWhitespace()
        if (!reader.atEnd) reader.fail("unexpected text after the end")
        return value
    }

    private class Reader(val text: String) {
        var pos = 0
        val atEnd get() = pos >= text.length

        fun fail(message: String): Nothing {
            val before = text.substring(0, pos.coerceAtMost(text.length))
            val line = before.count { it == '\n' } + 1
            val column = pos - (before.lastIndexOf('\n') + 1) + 1
            throw ParseException("line $line, column $column: $message")
        }

        fun skipWhitespace() {
            while (!atEnd && text[pos] in " \t\r\n") pos++
        }

        fun expect(c: Char) {
            if (atEnd || text[pos] != c) fail("expected '$c'")
            pos++
        }

        fun value(): Any? {
            if (atEnd) fail("unexpected end of file")
            return when (text[pos]) {
                '{' -> obj()
                '[' -> array()
                '"' -> string()
                't' -> literal("true", true)
                'f' -> literal("false", false)
                'n' -> literal("null", null)
                else -> number()
            }
        }

        fun literal(word: String, value: Any?): Any? {
            if (!text.startsWith(word, pos)) fail("unexpected character '${text[pos]}'")
            pos += word.length
            return value
        }

        fun obj(): Map<String, Any?> {
            expect('{')
            val map = LinkedHashMap<String, Any?>()
            skipWhitespace()
            if (!atEnd && text[pos] == '}') { pos++; return map }
            while (true) {
                skipWhitespace()
                if (atEnd || text[pos] != '"') fail("expected a key in quotes")
                val key = string()
                if (key in map) fail("\"$key\" appears twice")
                skipWhitespace()
                expect(':')
                skipWhitespace()
                map[key] = value()
                skipWhitespace()
                if (!atEnd && text[pos] == ',') { pos++; continue }
                if (!atEnd && text[pos] == '}') { pos++; return map }
                fail("expected ',' or '}' (a missing comma?)")
            }
        }

        fun array(): List<Any?> {
            expect('[')
            val list = ArrayList<Any?>()
            skipWhitespace()
            if (!atEnd && text[pos] == ']') { pos++; return list }
            while (true) {
                skipWhitespace()
                list += value()
                skipWhitespace()
                if (!atEnd && text[pos] == ',') { pos++; continue }
                if (!atEnd && text[pos] == ']') { pos++; return list }
                fail("expected ',' or ']' (a missing comma?)")
            }
        }

        fun string(): String {
            expect('"')
            val out = StringBuilder()
            while (true) {
                if (atEnd) fail("unterminated string")
                when (val c = text[pos++]) {
                    '"' -> return out.toString()
                    '\\' -> {
                        if (atEnd) fail("unterminated string")
                        when (val e = text[pos++]) {
                            '"', '\\', '/' -> out.append(e)
                            'b' -> out.append('\b')
                            'f' -> out.append('\u000C')
                            'n' -> out.append('\n')
                            'r' -> out.append('\r')
                            't' -> out.append('\t')
                            'u' -> {
                                val hex = text.substring(pos, (pos + 4).coerceAtMost(text.length))
                                val code = hex.takeIf { it.length == 4 }?.toIntOrNull(16) ?: fail("bad \\u escape")
                                out.append(code.toChar())
                                pos += 4
                            }
                            else -> { pos--; fail("bad escape '\\$e'") }
                        }
                    }
                    '\n' -> { pos--; fail("line break inside a string (use \\n, or split a story into a list of sentences)") }
                    else -> out.append(c)
                }
            }
        }

        fun number(): Double {
            val start = pos
            if (!atEnd && text[pos] == '-') pos++
            while (!atEnd && (text[pos].isDigit() || text[pos] in ".eE+-")) pos++
            val token = text.substring(start, pos)
            if (token.isEmpty() || token == "-") fail("unexpected character '${text[start]}'")
            return token.toDoubleOrNull()?.takeIf { it.isFinite() } ?: run { pos = start; fail("bad number \"$token\"") }
        }
    }
}

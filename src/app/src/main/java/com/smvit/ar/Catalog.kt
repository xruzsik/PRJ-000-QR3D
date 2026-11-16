package com.smvit.ar

import android.content.res.AssetManager
import com.smvit.ar.qr.Mat3
import com.smvit.ar.qr.RigidPose
import com.smvit.ar.qr.Vec3

/**
 * What appears on one QR code. Codes carry only "smvit-ar:<id>"; everything else
 * comes from the route file (assets/route.json), so changing a model or its
 * placement needs no new print.
 */
data class CatalogEntry(
    val id: String,
    val name: String,
    /** glTF binary under app/src/main/assets. */
    val model: String,
    /** Printed edge length of the QR symbol, without its white margin. */
    val qrSizeMeters: Double,
    /** Length of the model's longest side as shown (it is rescaled to this). */
    val modelSizeMeters: Float,
    /**
     * Where the model's bottom-centre sits, and how it's turned, relative to the
     * code in an upright frame ([com.smvit.ar.qr.Frames.upright]): +Y straight
     * up, +Z level towards the reader, +X the code's right. For a code 1.2 m up
     * a post, `offset(x = 0.8, y = -1.2)` stands the model on the ground 0.8 m
     * to its right, facing the reader.
     */
    val offset: RigidPose = RigidPose.IDENTITY,
    /** Animation (by its name in the GLB) to loop, or null for a still model. */
    val animation: String? = null,
    /** Short story shown with the character (Slovak), or null for none. */
    val story: String? = null,
    /**
     * Length when it leaves the code and roams the floor with the player
     * ([com.smvit.ar.companion.Companion]), or null to stay on the code.
     */
    val roamSizeMeters: Double? = null,
    /** Animations while roaming; idle uses [animation]. */
    val walkAnimation: String? = null,
    val runAnimation: String? = null,
) {
    val payload get() = Catalog.PAYLOAD_PREFIX + id
}

/**
 * The route: its characters, the order of its stops, and extra printed codes
 * that stand in for characters. Read from assets/route.json; the format is
 * described in the README.
 */
class Catalog(
    val entries: List<CatalogEntry>,
    /** The characters to collect, in walking order; finding all earns the badge. */
    val trail: List<String>,
    /** Extra code ids (without the prefix) that show an existing character. */
    aliases: Map<String, String> = emptyMap(),
    /** Name of the route, for the start screen. */
    val routeName: String? = null,
    val lengthKm: Double? = null,
    val minutes: Int? = null,
) {
    private val byId = entries.associateBy { it.id }
    private val byPayload = byId.mapKeys { PAYLOAD_PREFIX + it.key } +
        aliases.map { (code, id) -> PAYLOAD_PREFIX + code to byId.getValue(id) }

    init {
        require(entries.size == byId.size) { "duplicate character ids" }
        require(trail.all { it in byId }) { "unknown character in stops" }
        require(aliases.values.all { it in byId }) { "unknown character in aliases" }
    }

    fun byId(id: String): CatalogEntry? = byId[id]

    fun forPayload(payload: String): CatalogEntry? = byPayload[payload]

    class FormatException(message: String) : IllegalArgumentException(message)

    companion object {
        const val PAYLOAD_PREFIX = "smvit-ar:"
        const val ASSET = "route.json"

        fun load(assets: AssetManager): Catalog {
            val catalog = assets.open(ASSET).use { fromJson(it.readBytes().decodeToString(), ASSET) }
            for (entry in catalog.entries) {
                try {
                    assets.open(entry.model).close()
                } catch (e: java.io.IOException) {
                    throw FormatException("$ASSET: character \"${entry.id}\": no model file assets/${entry.model}")
                }
            }
            return catalog
        }

        /** Parses and checks a route file; errors name the file, the character and the field. */
        fun fromJson(text: String, source: String = ASSET): Catalog {
            val root = try {
                Json.parse(text)
            } catch (e: Json.ParseException) {
                throw FormatException("$source: ${e.message}")
            }
            return Fields(root, source).run {
                allowOnly("name", "lengthKm", "minutes", "stops", "aliases", "characters")
                val entries = list("characters").mapIndexed { i, it ->
                    val where = (it as? Map<*, *>)?.get("id") as? String ?: "#${i + 1}"
                    entry(Fields(it, "$source: character \"$where\""))
                }
                val ids = entries.map { it.id }.toSet()
                for (id in entries.groupingBy { it.id }.eachCount().filterValues { it > 1 }.keys) {
                    fail("character \"$id\" is defined twice")
                }
                val stops = list("stops").mapIndexed { i, it -> it as? String ?: fail("stops[$i] must be a character id in quotes") }
                if (stops.isEmpty()) fail("\"stops\" is empty")
                for (id in stops) if (id !in ids) fail("stop \"$id\" is not one of the characters")
                for (id in stops.groupingBy { it }.eachCount().filterValues { it > 1 }.keys) fail("stop \"$id\" is listed twice")
                val aliases = optionalMap("aliases").mapValues { (code, id) ->
                    val target = id as? String ?: fail("alias \"$code\" must be a character id in quotes")
                    if (target !in ids) fail("alias \"$code\" points to unknown character \"$target\"")
                    if (code in ids) fail("alias \"$code\" has the same id as a character")
                    target
                }
                Catalog(
                    entries = entries,
                    trail = stops,
                    aliases = aliases,
                    routeName = optionalString("name"),
                    lengthKm = optionalNumber("lengthKm", 0.0..100.0),
                    minutes = optionalNumber("minutes", 1.0..1440.0)?.toInt(),
                )
            }
        }

        private fun entry(f: Fields): CatalogEntry = f.run {
            allowOnly("id", "name", "model", "qrSizeMeters", "modelSizeMeters", "offset", "story", "roamSizeMeters", "animations")
            val id = string("id")
            if (!id.matches(Regex("[a-z0-9_]+"))) fail("\"id\" may only use a-z, 0-9 and _")
            val animations = optionalObject("animations")?.apply { allowOnly("idle", "walk", "run") }
            val offset = optionalObject("offset")?.run {
                allowOnly("x", "y", "z", "yawDegrees")
                offset(
                    x = optionalNumber("x", -10.0..10.0) ?: 0.0,
                    y = optionalNumber("y", -10.0..10.0) ?: 0.0,
                    z = optionalNumber("z", -10.0..10.0) ?: 0.0,
                    yawDegrees = optionalNumber("yawDegrees", -360.0..360.0) ?: 0.0,
                )
            }
            CatalogEntry(
                id = id,
                name = string("name"),
                model = string("model"),
                qrSizeMeters = number("qrSizeMeters", 0.02..0.5),
                modelSizeMeters = number("modelSizeMeters", 0.01..5.0).toFloat(),
                offset = offset ?: RigidPose.IDENTITY,
                animation = animations?.optionalString("idle"),
                story = optionalText("story"),
                roamSizeMeters = optionalNumber("roamSizeMeters", 0.05..5.0),
                walkAnimation = animations?.optionalString("walk"),
                runAnimation = animations?.optionalString("run"),
            )
        }

        /** Offset helper: [x], [y], [z] in metres in the anchor frame, turned [yawDegrees] about its +Y. */
        fun offset(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, yawDegrees: Double = 0.0) =
            RigidPose(Mat3.rotation(Vec3(0.0, 1.0, 0.0), Math.toRadians(yawDegrees)), Vec3(x, y, z))
    }

    /** Typed access to one JSON object, with errors that say where. */
    private class Fields(value: Any?, val where: String) {
        val map: Map<*, *> = value as? Map<*, *> ?: throw FormatException("$where: must be an object { ... }")

        fun fail(message: String): Nothing = throw FormatException("$where: $message")

        fun allowOnly(vararg keys: String) {
            for (key in map.keys) if (key !in keys) fail("unknown field \"$key\" (allowed: ${keys.joinToString()})")
        }

        fun string(key: String) = optionalString(key) ?: fail("\"$key\" is missing")

        fun optionalString(key: String): String? = when (val v = map[key]) {
            null -> null
            is String -> v.ifBlank { fail("\"$key\" is empty") }
            else -> fail("\"$key\" must be text in quotes")
        }

        /** Text given either as one string or as a list of strings (joined with spaces). */
        fun optionalText(key: String): String? = when (val v = map[key]) {
            is List<*> -> v.joinToString(" ") { it as? String ?: fail("\"$key\" must be a list of texts in quotes") }
                .ifBlank { fail("\"$key\" is empty") }
            else -> optionalString(key)
        }

        fun number(key: String, range: ClosedFloatingPointRange<Double>) =
            optionalNumber(key, range) ?: fail("\"$key\" is missing")

        fun optionalNumber(key: String, range: ClosedFloatingPointRange<Double>): Double? = when (val v = map[key]) {
            null -> null
            is Double -> v.takeIf { it in range } ?: fail("\"$key\" is $v, expected ${range.start}..${range.endInclusive}")
            else -> fail("\"$key\" must be a number (no quotes)")
        }

        fun list(key: String): List<*> = map[key] as? List<*> ?: fail("\"$key\" must be a list [ ... ]")

        fun optionalMap(key: String): Map<String, Any?> = when (val v = map[key]) {
            null -> emptyMap()
            is Map<*, *> -> v.entries.associate { (k, value) -> k as String to value }
            else -> fail("\"$key\" must be an object { ... }")
        }

        fun optionalObject(key: String): Fields? = map[key]?.let { Fields(it, "$where, \"$key\"") }
    }
}

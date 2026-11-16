package com.smvit.ar

import com.smvit.ar.qr.Mat3
import com.smvit.ar.qr.RigidPose
import com.smvit.ar.qr.Vec3

/**
 * What appears on one QR code. Codes carry only "smvit-ar:<id>"; everything else
 * is here, so changing a model or its placement needs no new print.
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

object Catalog {
    const val PAYLOAD_PREFIX = "smvit-ar:"

    val entries = listOf(
        CatalogEntry(
            id = "axis_cube",
            name = "Testovacia kocka",
            model = "models/placeholder_cube.glb",
            qrSizeMeters = 0.10,
            modelSizeMeters = 0.05f,
        ),
        // Quaternius "Ultimate Animated Animals" (CC0), converted with
        // content/tools/gltf_to_glb.py. Sized and placed for testing beside a
        // printed code on a table; on the trail they'd be closer to life size
        // (fox ~0.9 m, wolf ~1.3 m, deer ~1.5 m, stag ~1.8 m long) and lower,
        // standing on the ground beside the post.
        // Stories are drafts for the team to review.
        animal(
            "fox", "Líška", sizeMeters = 0.35f, besideMeters = 0.20, roamMeters = 0.8,
            story = "Líška hrdzavá žije na okrajoch lesov po celom Slovensku. Má výborný sluch – " +
                "počuje aj myš, ktorá zašuchoce pod snehom. Svoj huňatý chvost používa v zime ako prikrývku.",
        ),
        animal(
            "wolf", "Vlk", sizeMeters = 0.40f, besideMeters = 0.22, roamMeters = 1.1,
            story = "Vlk dravý žije v horských lesoch Karpát, napríklad na Poľane či v Tatrách. " +
                "Žije vo svorke, ktorá spolu loví aj vychováva mláďatá. Človeku sa zvyčajne vyhne " +
                "skôr, než ho vôbec zbadáš.",
        ),
        animal(
            "deer", "Srna", sizeMeters = 0.50f, besideMeters = 0.25, roamMeters = 1.1,
            story = "Srnec hôrny je náš najmenší jeleňovitý druh. Srny vychádzajú ráno a večer z lesa " +
                "pásť sa na lúky. Mláďatá majú na srsti biele škvrny, vďaka ktorým sú v tráve takmer neviditeľné.",
        ),
        animal(
            "stag", "Jeleň", sizeMeters = 0.55f, besideMeters = 0.30, roamMeters = 1.4,
            story = "Jeleň lesný je jedným z našich najväčších lesných zvierat. Parohy každú jar zhodí " +
                "a do leta mu narastú nové. Na jeseň sa lesom nesie jeho hlasné ručanie.",
        ),
    )

    /** The characters to collect on the trail, in walking order; finding all earns the badge. */
    val trail = listOf("fox", "wolf", "deer", "stag")

    private fun animal(id: String, name: String, sizeMeters: Float, besideMeters: Double, roamMeters: Double, story: String) =
        CatalogEntry(
            id = id,
            name = name,
            model = "models/$id.glb",
            qrSizeMeters = 0.10,
            modelSizeMeters = sizeMeters,
            offset = offset(x = besideMeters),
            animation = "Idle",
            story = story,
            // Smaller than life (fox ~0.9 m, stag ~1.8 m) so they fit on screen nearby.
            roamSizeMeters = roamMeters,
            walkAnimation = "Walk",
            runAnimation = "Gallop",
        )

    fun byId(id: String): CatalogEntry? = entries.firstOrNull { it.id == id }

    /**
     * TEMPORARY: the only codes printed so far are the two on
     * content/markers/qr-test-sheet.pdf, and no new prints are possible for a
     * while, so they stand in for animals. Remove once animals.pdf is printed.
     */
    private val printedStandIns = mapOf(
        "test_cube" to "fox",
        "test_cube_2" to "deer",
    )

    private val byPayload = entries.associateBy { it.payload } +
        printedStandIns.map { (printed, id) -> PAYLOAD_PREFIX + printed to entries.single { it.id == id } }

    fun forPayload(payload: String): CatalogEntry? = byPayload[payload]

    /** Offset helper: [x], [y], [z] in metres in the anchor frame, turned [yawDegrees] about its +Y. */
    fun offset(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, yawDegrees: Double = 0.0) =
        RigidPose(Mat3.rotation(Vec3(0.0, 1.0, 0.0), Math.toRadians(yawDegrees)), Vec3(x, y, z))
}

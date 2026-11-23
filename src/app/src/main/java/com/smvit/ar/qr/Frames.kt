package com.smvit.ar.qr

/** Fixed changes of coordinate frame between the camera, the QR and the anchor. */
object Frames {

    /**
     * ARCore's camera pose is an OpenGL camera (+X right, +Y up, looking along -Z);
     * the solver works in the computer-vision camera (+Y down, looking along +Z).
     */
    val glCameraFromCvCamera = RigidPose(
        Mat3.fromRows(Vec3(1.0, 0.0, 0.0), Vec3(0.0, -1.0, 0.0), Vec3(0.0, 0.0, -1.0)),
        Vec3.ZERO,
    )

    /**
     * The anchor frame placed models are attached to, expressed in the QR frame.
     * Anchor +Y is the QR normal (out of the printed face), so a Y-up model stands
     * upright on a code lying flat; anchor +Z points at the code's bottom edge, so
     * the model faces whoever is reading the code; anchor +X is the code's +X.
     */
    val qrFromAnchor = RigidPose(
        Mat3.fromColumns(Vec3(1.0, 0.0, 0.0), Vec3(0.0, 0.0, 1.0), Vec3(0.0, -1.0, 0.0)),
        Vec3.ZERO,
    )

    /** World pose of the anchor frame for a QR seen from a camera at [worldFromGlCamera]. */
    fun worldFromAnchor(worldFromGlCamera: RigidPose, cvCameraFromQr: RigidPose): RigidPose =
        worldFromGlCamera compose glCameraFromCvCamera compose cvCameraFromQr compose qrFromAnchor

    /**
     * The frame characters are placed in: at the code, +Y straight up (ARCore's
     * world is gravity-aligned), +Z level and towards whoever reads the code, and
     * +X to the code's right as they see it.
     *
     * A code on a post or tree faces sideways, so its own frame would lay a
     * character on its side; this one keeps it standing. "Towards the reader" is
     * the code's normal for an upright or tilted code, and its bottom edge for
     * one lying (nearly) flat, where the normal points at the sky.
     */
    fun upright(anchor: RigidPose, worldUp: Vec3 = Vec3(0.0, 1.0, 0.0)): RigidPose {
        val up = worldUp.normalized()
        fun level(v: Vec3) = v - up * (v dot up)
        val normal = level(anchor.rotation.column(1))
        // sin 30 deg: tilted less than 30 deg from flat counts as lying flat.
        val forward = if (normal.norm() > 0.5) normal else level(anchor.rotation.column(2))
        val z = forward.normalized()
        return RigidPose(Mat3.fromColumns(up cross z, up, z), anchor.translation)
    }

    /**
     * [upright] lowered to the ground at height [groundY] (world +Y up), for a
     * code on a post or wall. A code already near the ground (lying on it, or the
     * floor estimate is off) stays where it is.
     */
    fun onGround(upright: RigidPose, groundY: Double): RigidPose {
        val t = upright.translation
        return if (t.y - groundY < MIN_HEIGHT_FOR_GROUND) upright else RigidPose(upright.rotation, Vec3(t.x, groundY, t.z))
    }

    /** Codes less than this above the ground are treated as lying on it. */
    const val MIN_HEIGHT_FOR_GROUND = 0.3
}

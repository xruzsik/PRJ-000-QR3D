package com.smvit.ar.qr

import org.junit.Assert.assertEquals
import org.junit.Test

class OnGroundTest {
    private val upright = RigidPose(Mat3.rotation(Vec3(0.0, 1.0, 0.0), 0.7), Vec3(1.0, 0.2, -2.0))

    @Test
    fun codeOnAPostDropsToTheGroundKeepingPlaceAndHeading() {
        val placed = Frames.onGround(upright, groundY = -1.1)
        assertEquals(Vec3(1.0, -1.1, -2.0), placed.translation)
        assertEquals(upright.rotation, placed.rotation)
    }

    @Test
    fun codeNearTheGroundStaysPut() {
        assertEquals(upright, Frames.onGround(upright, groundY = 0.0))
        assertEquals(upright, Frames.onGround(upright, groundY = 0.5)) // floor estimate above the code
    }
}

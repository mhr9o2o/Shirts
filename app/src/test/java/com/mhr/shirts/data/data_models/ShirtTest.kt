package com.mhr.shirts.data.data_models

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class ShirtTest {

    @Test
    fun equalsForOtherType() {
        assertEquals(false, equals("A String"))
    }

    @Test
    fun equalsForSameShirtWithDifferentId()
    {
        val first = Shirt(0, "Shirt", 1000, "white", 2, "m", "url")
        val second = Shirt(1, "Shirt", 1000, "white", 2, "m", "url")
        assertEquals(false, first == second)
    }

    @Test
    fun equalsForSameIdWithDifferentParams()
    {
        val first = Shirt(0, "Shirt", 1000, "white", 2, "m", "url")
        val second = Shirt(0, "Pants", 2000, "brown", 1, "xxl", "another_url")
        assertEquals(true, first == second)
    }

}
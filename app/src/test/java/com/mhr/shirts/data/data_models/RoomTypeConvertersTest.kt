package com.mhr.shirts.data.data_models

import org.junit.Test

import org.junit.Assert.*

class RoomTypeConvertersTest {

    @Test
    fun fromEmptyShirtList() {
        val shirts: List<Shirt> = emptyList()
        val expectedJson = "[]"
        assertEquals(expectedJson, RoomTypeConverters.fromShirtList(shirts))
    }

    @Test
    fun fromShirtList()
    {
        val shirt = Shirt(0, "", 10, "", 1, "", "")
        val shirts = listOf(shirt)
        val expectedJson = "[{\"id\":0,\"name\":\"\",\"price\":10,\"colour\":\"\",\"quantity\":1,\"size\":\"\",\"picture\":\"\"}]"
        assertEquals(expectedJson, RoomTypeConverters.fromShirtList(shirts))
    }

    @Test
    fun toEmptyShirtList() {
        val json = "[]"
        val emptyShirtList: List<Shirt> = emptyList()
        assertEquals(RoomTypeConverters.toShirtList(json), emptyShirtList)
    }

    @Test
    fun toShirtList() {
        val json = "[{\"id\":0,\"name\":\"\",\"price\":10,\"colour\":\"\",\"quantity\":1,\"size\":\"\",\"picture\":\"\"}]"
        val shirt = Shirt(0, "", 10, "", 1, "", "")
        val shirts = RoomTypeConverters.toShirtList(json)
        assertEquals(1, shirts.size)
        assertEquals(shirt.id, shirts[0].id)
        assertEquals(shirt.name, shirts[0].name)
        assertEquals(shirt.price, shirts[0].price)
        assertEquals(shirt.colour, shirts[0].colour)
        assertEquals(shirt.quantity, shirts[0].quantity)
        assertEquals(shirt.size, shirts[0].size)
        assertEquals(shirt.picture, shirts[0].picture)

    }

}
package com.mhr.shirts.data.data_models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Assert.*
import org.junit.Test

class BasketTest {

    @Test
    fun convertingEmptyBasketToJson()
    {
        val emptyBasket = Basket()
        val gson = Gson()
        val expectedJson = "{\"shirts\":[]}"
        val convertedJson = gson.toJson(emptyBasket)
        assertEquals(expectedJson, convertedJson)
    }

    @Test
    fun convertingBasketToJson()
    {
        val shirt = Shirt(0, "name", 20, "white", 3, "l", "url")
        val basket = Basket(0, mutableListOf(shirt))
        val expectedJson = "{\"shirts\":[{\"id\":0,\"name\":\"name\",\"price\":20,\"colour\":\"white\",\"quantity\":3,\"size\":\"l\",\"picture\":\"url\"}]}"
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        val convertedJson = gson.toJson(basket)
        assertEquals(expectedJson, convertedJson)
    }

}
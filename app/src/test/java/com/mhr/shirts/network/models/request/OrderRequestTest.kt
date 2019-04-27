package com.mhr.shirts.network.models.request

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import org.junit.Assert.*
import org.junit.Test

class OrderRequestTest {

    @Test
    fun convertingModelToJson()
    {
        val order = OrderRequest(10, Basket())
        val expectedJson = "{\"total\":10,\"basket\":{\"shirts\":[]}}"
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        val convertedJson = gson.toJson(order)
        assertEquals(expectedJson, convertedJson)
    }

    @Test
    fun validityOfEmptyOrder()
    {
        val order = OrderRequest(10, Basket())
        assertEquals(false, order.validate())
    }

    @Test
    fun validityOfWrongTotal()
    {
        val shirts = mutableListOf(Shirt(0, "", 20, "", 2, "", ""))
        val basket = Basket(0, shirts)
        val order = OrderRequest(10, basket)
        assertEquals(false, order.validate())
    }

    @Test
    fun validityOfCorrectTotal()
    {
        val shirts = mutableListOf(Shirt(0, "", 20, "", 2, "", ""))
        val basket = Basket(0, shirts)
        val order = OrderRequest(20, basket)
        assertEquals(true, order.validate())
    }

}
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

}
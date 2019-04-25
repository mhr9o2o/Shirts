package com.mhr.shirts.data.data_models

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class ShirtTest {

    //region Equality Test
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
    //endregion

    //region Gson Test
    @Test
    fun convertingCorrectJsonToModel()
    {
        val json = "{\n" +
                "    \"id\": 0,\n" +
                "    \"price\": 88,\n" +
                "    \"picture\": \"https://unsplash.it/128/128\",\n" +
                "    \"colour\": \"brown\",\n" +
                "    \"size\": \"m\",\n" +
                "    \"name\": \"Southview Clarke\",\n" +
                "    \"quantity\": 2\n" +
                "  }"
        val gson = Gson()
        val convertedModel: Shirt = gson.fromJson(json, Shirt::class.java)
        assertEquals(convertedModel.id, 0)
        assertEquals(convertedModel.price,88)
        assertEquals(convertedModel.picture,"https://unsplash.it/128/128")
        assertEquals(convertedModel.colour,"brown")
        assertEquals(convertedModel.size,"m")
        assertEquals(convertedModel.name, "Southview Clarke")
        assertEquals(convertedModel.quantity,2)
    }

    @Test
    fun testingNullSafety()
    {
        val json = "{\n" +
                "    \"id\": null,\n" +
                "    \"price\": null,\n" +
                "    \"picture\": null,\n" +
                "    \"colour\": null,\n" +
                "    \"size\": null,\n" +
                "    \"name\": null,\n" +
                "    \"quantity\": null\n" +
                "  }"
        val gson = Gson()
        val convertedModel: Shirt = gson.fromJson(json, Shirt::class.java)
        assertEquals(convertedModel.id, null)
        assertEquals(convertedModel.price,null)
        assertEquals(convertedModel.picture,null)
        assertEquals(convertedModel.colour,null)
        assertEquals(convertedModel.size,null)
        assertEquals(convertedModel.name, null)
        assertEquals(convertedModel.quantity,null)

    }
    //endregion

}
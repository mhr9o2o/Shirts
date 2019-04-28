package com.mhr.shirts.network.models.response

import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*

class SuccessfulOrderResponseTest {

    @Test
    fun equalsForOtherType() {
        val response = SuccessfulOrderResponse()
        assertEquals(false, response.equals("A String"))
    }

    @Test
    fun equalsForSameParamsExceptId()
    {
        val response1 = SuccessfulOrderResponse()
        val response2 = SuccessfulOrderResponse("order-id", 0, "", emptyList())
        assertEquals(false, response1 == response2)
    }

    @Test
    fun equalsForSameIdWithDifferentParams()
    {
        val response1 = SuccessfulOrderResponse()
        val response2 = SuccessfulOrderResponse("order-id", 2, "pending", emptyList())
        assertEquals(true, response1 == response2)
    }

    @Test
    fun convertingCorrectJsonToResponseModel()
    {
        val json = "{\n" +
                "  \"id\": \"order-id\",\n" +
                "  \"total\": 0,\n" +
                "  \"status\": \"string\",\n" +
                "  \"shirts\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\",\n" +
                "      \"price\": 0,\n" +
                "      \"colour\": \"string\",\n" +
                "      \"quantity\": 0,\n" +
                "      \"size\": \"string\",\n" +
                "      \"picture\": \"string\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        val gson = Gson()
        val response = gson.fromJson<SuccessfulOrderResponse>(json, SuccessfulOrderResponse::class.java)

        assertNotNull(response)
        assertEquals(response.id, "order-id")
        assertEquals(response.total, 0)
        assertEquals(response.status, "string")
        assertNotNull(response.shirts)
        assertEquals(response.shirts!!.size, 1)
        assertEquals(response.shirts!![0].id, 0)
        assertEquals(response.shirts!![0].name, "string")
        assertEquals(response.shirts!![0].price, 0)
        assertEquals(response.shirts!![0].colour, "string")
        assertEquals(response.shirts!![0].quantity, 0)
        assertEquals(response.shirts!![0].size, "string")
        assertEquals(response.shirts!![0].picture, "string")

    }

}
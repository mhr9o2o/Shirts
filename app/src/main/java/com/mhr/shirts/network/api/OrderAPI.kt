package com.mhr.shirts.network.api

import com.mhr.shirts.network.models.request.OrderRequest
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderAPI {

    @POST("/jayson")
    fun orderBasket(@Body order: OrderRequest): Observable<SuccessfulOrderResponse>

    companion object
    {
        fun create(baseUrl: String): OrderAPI
        {
            val retrofitRequest = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

            return retrofitRequest.create(OrderAPI::class.java)
        }
    }

}
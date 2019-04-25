package com.mhr.shirts.network.api

import com.mhr.shirts.network.models.request.OrderRequest
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderAPI {

    @POST("/jayson")
    fun orderBasket(@Body order: OrderRequest): Observable<SuccessfulOrderResponse>

}
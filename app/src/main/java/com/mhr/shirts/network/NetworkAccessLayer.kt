package com.mhr.shirts.network

import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.network.api.OrderAPI
import com.mhr.shirts.network.api.ShirtsAPI
import com.mhr.shirts.network.models.request.OrderRequest
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable

class NetworkAccessLayer {

    //region URLs
    val schema = "https://"
    val baseUrl = "interview.test.unwire.com"
    val shirtsPath = "/shirts"
    val orderPath = "/order"
    //endregion

    //region Api Services
    val orderApiService by lazy {
        OrderAPI.create(schema + baseUrl + orderPath)
    }

    val shirtsApiService by lazy {
        ShirtsAPI.create(schema + baseUrl + shirtsPath)
    }
    //endregion

    //region APIs
    fun fetchAllShirts() : Observable<List<Shirt>>
    {
        return shirtsApiService.fetchShirts()
    }

    fun orderBasket(order: OrderRequest) : Observable<SuccessfulOrderResponse>
    {
        return orderApiService.orderBasket(order)
    }
    //endregion

}
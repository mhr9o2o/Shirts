package com.mhr.shirts.network

import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.network.api.OrderAPI
import com.mhr.shirts.network.api.ShirtsAPI
import com.mhr.shirts.network.models.request.OrderRequest
import com.mhr.shirts.network.models.response.SuccessfulOrderResponse
import io.reactivex.Observable

/**
 * NetworkAccessLayer gives access to the APIs
 */
class NetworkAccessLayer {

    //region URLs
    val schema = "https://"
    val baseUrl = "interview.test.unwire.com/"
    //endregion

    //region Api Services
    val orderApiService by lazy {
        OrderAPI.create(schema + baseUrl)
    }

    val shirtsApiService by lazy {
        ShirtsAPI.create(schema + baseUrl)
    }
    //endregion

    //region APIs
    /**
     * Fetches shirts from the server
     * @return result observable
     */
    fun fetchAllShirts(): Observable<List<Shirt>> {
        return shirtsApiService.fetchShirts()
    }

    /**
     * Orders basket to the server
     * @return result observable
     */
    fun orderBasket(order: OrderRequest): Observable<SuccessfulOrderResponse> {
        return orderApiService.orderBasket(order)
    }
    //endregion

}
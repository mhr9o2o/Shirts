package com.mhr.shirts.network.api

import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import retrofit2.http.GET

interface ShirtsAPI {

    @GET
    fun fetchShirts(): Observable<List<Shirt>>

}
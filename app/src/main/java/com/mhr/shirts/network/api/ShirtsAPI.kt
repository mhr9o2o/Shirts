package com.mhr.shirts.network.api

import com.mhr.shirts.data.data_models.Shirt
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Retrofit Shirts API which creates shirts retrofit request
 */
interface ShirtsAPI {

    @GET("shirts/")
    fun fetchShirts(): Observable<List<Shirt>>

    companion object
    {
        fun create(baseUrl: String): ShirtsAPI
        {
            val retrofitRequest = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

            return retrofitRequest.create(ShirtsAPI::class.java)
        }
    }

}
package com.mhr.shirts.network.models.request

import com.google.gson.annotations.Expose
import com.mhr.shirts.data.data_models.Basket

data class OrderRequest(
    @Expose val total: Int = 0,
    @Expose val basket: Basket) {

    fun validate(): Boolean
    {
        if (basket.shirts.isEmpty())
        {
            return false
        }
        else
        {
            var totalCount = 0
            for (shirt in basket.shirts)
            {
                val price = shirt.price?:0
                totalCount += price
            }

            return totalCount == total

        }
    }

}
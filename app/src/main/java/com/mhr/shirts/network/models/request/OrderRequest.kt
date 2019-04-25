package com.mhr.shirts.network.models.request

import com.mhr.shirts.data.data_models.Basket

data class OrderRequest(val total: Int = 0, val basket: Basket) {

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
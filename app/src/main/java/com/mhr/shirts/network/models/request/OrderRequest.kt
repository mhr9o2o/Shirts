package com.mhr.shirts.network.models.request

import com.google.gson.annotations.Expose
import com.mhr.shirts.data.data_models.Basket

/**
 * Order Request Model
 */
data class OrderRequest(
    @Expose val total: Int = 0,
    @Expose val basket: Basket
)
package com.mhr.shirts.network.models.response

import com.mhr.shirts.data.data_models.Shirt

data class SuccessfulOrderResponse(val id: Int? = 0, val total: Int? = 0, val status: String? = "", val shirts: List<Shirt>? = emptyList())
{
    //region Overridden Functions
    override fun equals(other: Any?): Boolean {
        if (other is SuccessfulOrderResponse)
        {
            return other.id == id
        }
        else
        {
            return false;
        }
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
    //endregion
}
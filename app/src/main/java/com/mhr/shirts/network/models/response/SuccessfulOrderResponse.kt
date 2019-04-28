package com.mhr.shirts.network.models.response

import com.mhr.shirts.data.data_models.Shirt

/**
 * Successful Order Request's response
 * The equality is checked by id
 */
data class SuccessfulOrderResponse(
    val id: String? = "",
    val total: Int? = 0,
    val status: String? = "",
    val shirts: List<Shirt>? = emptyList()
) {
    //region Overridden Functions
    override fun equals(other: Any?): Boolean {
        return if (other is SuccessfulOrderResponse) {
            other.id == id
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    //endregion
}
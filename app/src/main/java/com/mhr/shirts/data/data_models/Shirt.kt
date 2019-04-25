package com.mhr.shirts.data.data_models

data class Shirt(val id: Int?, val name: String?, val price: Int?, val colour: String?, val quantity: Int?, val size: String?, val picture: String?)
{

    //region Overridden Functions
    override fun equals(other: Any?): Boolean {
        if (other is Shirt)
        {
            return this.id == other.id
        }
        else
        {
            return false
        }
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
    //endregion
}
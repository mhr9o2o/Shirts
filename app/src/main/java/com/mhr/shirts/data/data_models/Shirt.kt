package com.mhr.shirts.data.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity
data class Shirt(
    @Expose @PrimaryKey val id: Int?,
    @Expose val name: String?,
    @Expose val price: Int?,
    @Expose val colour: String?,
    @Expose val quantity: Int?,
    @Expose val size: String?,
    @Expose val picture: String?)
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
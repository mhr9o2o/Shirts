package com.mhr.shirts.data.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * Basket Model data class, also used as Room Entity
 * Id is fixed as we have only one instance of basket not more [Yes we could have no primary key but who knows the future?]
 */
@Entity
data class Basket(
    @PrimaryKey val id: Int = 0,
    @Expose val shirts: MutableList<Shirt> = mutableListOf()
)
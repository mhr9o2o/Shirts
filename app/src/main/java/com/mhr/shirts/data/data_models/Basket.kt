package com.mhr.shirts.data.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity
data class Basket(
    @PrimaryKey val id: Int = 0,
    @Expose val shirts: MutableList<Shirt> = mutableListOf()
)
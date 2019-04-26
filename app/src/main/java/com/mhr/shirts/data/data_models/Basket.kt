package com.mhr.shirts.data.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity
data class Basket(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @Expose val shirts: List<Shirt> = emptyList())
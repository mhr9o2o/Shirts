package com.mhr.shirts.data.database.dao

import androidx.room.*
import com.mhr.shirts.data.data_models.Shirt

@Dao
interface ShirtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShirt(shirt: Shirt)

    @Update
    fun updateShirt(shirt: Shirt)

    @Delete
    fun deleteShirt(shirt: Shirt)

    @Query("SELECT * FROM Shirt")
    fun getShirts() : List<Shirt>

    @Query("SELECT * FROM Shirt WHERE colour == :colour AND size == :size")
    fun filterShirtsByColourAndSize(colour: String, size: String) : List<Shirt>

    @Query("SELECT * FROM Shirt WHERE colour == :colour")
    fun filterShirtsByColour(colour: String) : List<Shirt>

    @Query("SELECT * FROM Shirt WHERE size == :size")
    fun filterShirtsBySize(size: String) : List<Shirt>

}
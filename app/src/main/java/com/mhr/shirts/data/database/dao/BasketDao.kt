package com.mhr.shirts.data.database.dao

import androidx.room.*
import com.mhr.shirts.data.data_models.Basket

/**
 * Basket Data Access Object for Room
 * It includes insertion, update, deletion and retrieving
 */
@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBasket(basket: Basket)

    @Update
    fun updateBasket(basket: Basket)

    @Delete
    fun deleteBasket(basket: Basket)

    @Query("SELECT * FROM Basket WHERE id == 0")
    fun getBasket(): Basket

}
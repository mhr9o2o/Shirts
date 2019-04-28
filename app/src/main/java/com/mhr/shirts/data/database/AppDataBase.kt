package com.mhr.shirts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.data.database.dao.BasketDao
import com.mhr.shirts.data.database.dao.ShirtDao

/**
 * Our Room Database
 * It includes our DAOs and an static instance
 */
@Database(entities = [Shirt::class, Basket::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun shirtDao(): ShirtDao
    abstract fun basketDao(): BasketDao

    companion object {
        var instance: AppDataBase? = null
        val DATABASE_NAME = "ShirtsDB"

        fun getAppDataBase(context: Context): AppDataBase? {
            if (instance == null) {
                synchronized(AppDataBase::class)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }

            return instance

        }

        fun killDataBase() {
            instance = null
        }

    }

}
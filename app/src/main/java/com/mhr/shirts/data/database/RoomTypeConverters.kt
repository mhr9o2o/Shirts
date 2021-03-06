package com.mhr.shirts.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mhr.shirts.data.data_models.Shirt

/**
 * This type converter is implemented to convert lists to SQL friendly type which is String here and vice versa
 */
class RoomTypeConverters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromShirtList(shirts: MutableList<Shirt>): String {
            val gson = Gson()
            return gson.toJson(shirts)
        }

        @TypeConverter
        @JvmStatic
        fun toShirtList(json: String): MutableList<Shirt> {
            val gson = Gson()
            val shirtsListType = object : TypeToken<List<Shirt>>() {}.type
            return gson.fromJson(json, shirtsListType)
        }

    }

}
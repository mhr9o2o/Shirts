package com.mhr.shirts.data.data_models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * Shirt Model data class, also used as Room entity
 * Equality is checked just by id
 * Model implements Parcelable as it needs to be serialized for being stored/transferred through bundles.
 */
@Entity
data class Shirt(
    @Expose @PrimaryKey val id: Int?,
    @Expose val name: String?,
    @Expose val price: Int?,
    @Expose val colour: String?,
    @Expose var quantity: Int?,
    @Expose val size: String?,
    @Expose val picture: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    //region Overridden Functions
    override fun equals(other: Any?): Boolean {
        return if (other is Shirt) {
            this.id == other.id
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return id ?: 0
    }

    //endregion
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(colour)
        parcel.writeValue(quantity)
        parcel.writeString(size)
        parcel.writeString(picture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Shirt> {
        override fun createFromParcel(parcel: Parcel): Shirt {
            return Shirt(parcel)
        }

        override fun newArray(size: Int): Array<Shirt?> {
            return arrayOfNulls(size)
        }
    }
}
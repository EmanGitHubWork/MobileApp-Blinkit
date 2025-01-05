package com.example.blinkit.models

import android.os.Parcel
import android.os.Parcelable

data class Category2(

    var title: String = "",
    var image: String = "",
    var gram: String = "",
    var price: String = "",
    var quantity: Int = 0 // Added quantity property to track item count in the cart
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() // Read quantity from Parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(gram)
        parcel.writeString(price)
        parcel.writeInt(quantity) // Write quantity to Parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category2> {
        override fun createFromParcel(parcel: Parcel): Category2 {
            return Category2(parcel)
        }

        override fun newArray(size: Int): Array<Category2?> {
            return arrayOfNulls(size)
        }
    }
}

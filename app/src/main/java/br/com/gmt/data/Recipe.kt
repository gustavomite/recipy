package br.com.gmt.data

import android.os.Parcel
import android.os.Parcelable

class Recipe (var name: String): Parcelable {
    var description = ""
    var time = 0
    var ingredientList = mutableMapOf<Int, String>()

    constructor(parcel: Parcel) : this(parcel.readString()!!) {
        description = parcel.readString()!!
        time = parcel.readInt()
        ingredientList = mutableMapOf()
        parcel.readMap(ingredientList, String::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(time)
        parcel.writeMap(ingredientList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}
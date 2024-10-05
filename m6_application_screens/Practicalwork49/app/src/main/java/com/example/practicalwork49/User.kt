package com.example.practicalwork49

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    val name: String = "",
    val lastName: String = "",
    val patronymic: String = ""
) : Parcelable{
    override fun toString(): String {
        return "$lastName $name $patronymic"
    }
}
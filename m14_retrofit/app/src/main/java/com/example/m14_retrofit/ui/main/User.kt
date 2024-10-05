package com.example.m14_retrofit.ui.main

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("results")
    val user: List<User>
)
data class User(
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("name") val name: Name? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("location") val location: Location? = null,
    @SerializedName("phone") val phone : String? = null,
    @SerializedName("picture") var picture: Picture? = null,
    @SerializedName("registered") var registered : Registered? = null,
)

data class Name(
    @SerializedName("title") val title: String? = null,
    @SerializedName("first") val first: String? = null,
    @SerializedName("last") val last: String? = null
){
    override fun toString() = "$title $first $last"
}

data class Location(
    @SerializedName("street") val street: Street? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state : String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("coordinates") var coordinates : Coordinates? = null
)


data class Street(
    @SerializedName("number") val number: Int? = null,
    @SerializedName("name") val name: String? = null
)

data class Picture(
    @SerializedName("large") var large : String? = null
)

data class Registered(
    @SerializedName("date") val date : String? = null,
    @SerializedName("age") val age : Int? = null
)

data class Coordinates(
    @SerializedName("latitude") val latitude : String? = null,
    @SerializedName("longitude") val longitude : String? = null
)
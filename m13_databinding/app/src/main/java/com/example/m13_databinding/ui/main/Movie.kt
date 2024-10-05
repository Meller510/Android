package com.example.m13_databinding.ui.main

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title")
    var title: String? = null,
    @SerializedName("Year")
    val year: String? = null,
    @SerializedName("Poster")
    val poster: String? = null
){
    fun isEmpty() = title == null && year == null && poster == null
}



package com.example.m12_mvvm.ui.main

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title")
    var title: String?,
    @SerializedName("Year")
    val year: String?,
    @SerializedName("Poster")
    val poster: String?
){
    fun isEmpty() = title == null && year == null && poster == null
}



package com.example.m17_recyclerview.entity

import com.google.gson.annotations.SerializedName


interface MarsPhoto {
    val id: Int
    val sol: Int
    val camera: Camera
    val imgSrc: String
    val earthDate: String
    val rover: Rover
}

data class Camera(
    val id: Int,
    val name: String,
    @SerializedName("rover_id") val roverId: Int,
    @SerializedName("full_name") val fullName: String
)

data class Rover(
    val id: Int,
    val name: String,
    val status: String,
    val cameras: List<Camera>,
    @SerializedName("landing_date") val landingDate: String,
    @SerializedName("launch_date") val launchDate: String,
    @SerializedName("max_sol") val maxSol: Int,
    @SerializedName("max_date") val maxDate: String,
    @SerializedName("total_photos") val totalPhotos: Int
)
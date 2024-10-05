package com.example.m17_recyclerview.data

import android.os.Parcel
import com.example.m17_recyclerview.entity.Camera
import com.example.m17_recyclerview.entity.MarsPhoto
import com.example.m17_recyclerview.entity.Rover
import com.google.gson.annotations.SerializedName

class MarsPhotos{
    val photos : List<MarsPhotoDto> = emptyList()
}

class MarsPhotoDto (
    override val id: Int,
    override val sol: Int,
    override val camera: Camera,
    @SerializedName("img_src") override val imgSrc: String,
    @SerializedName("earth_date")override val earthDate: String,
    override val rover: Rover
): MarsPhoto

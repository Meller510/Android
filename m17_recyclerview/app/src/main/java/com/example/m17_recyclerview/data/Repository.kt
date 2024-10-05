package com.example.m17_recyclerview.data;

import javax.inject.Inject

class Repository @Inject constructor(
    private val marsPhotoDataSource: MarsPhotoDataSource
) {
    suspend fun getMarsPhotos(page: Int) = marsPhotoDataSource.loadMarsPhotos(page)
}

package com.example.m17_recyclerview.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private const val MOVIE_BASE_URl = "https://api.nasa.gov"
private const val API_KEY = "fKnuGXy8aP9Xd0F1f14b1gYSnn3Bil8LHqneykkG"

class MarsPhotoDataSource @Inject constructor() {
    companion object RetrofitServices {
        private val retrofit = Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val photoListApi: PhotoListApi = retrofit.create(PhotoListApi::class.java)
    }

    suspend fun loadMarsPhotos(page: Int) = photoListApi.getData(page, API_KEY)
}

interface PhotoListApi {
    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=1000&")
    suspend fun getData(@Query("page") title: Int, @Query("api_key") apiKey: String): MarsPhotos
}

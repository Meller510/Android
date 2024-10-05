package com.example.m14_retrofit.ui.main

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val MOVIE_BASE_URl = "https://randomuser.me"
class WebRepository : MovieRepository {
    companion object RetrofitServices {
        private val retrofit = Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userDataApi: GetUserDataApi = retrofit.create(GetUserDataApi::class.java)
    }

    override suspend fun getUserData() = userDataApi.getData().user.first()
}
interface GetUserDataApi {
    @GET("/api")
    suspend fun getData(): Results
}
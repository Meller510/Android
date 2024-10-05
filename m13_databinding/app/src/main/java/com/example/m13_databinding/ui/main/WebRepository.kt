package com.example.m13_databinding.ui.main

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "642aa880"
private const val MOVIE_BASE_URl = "https://www.omdbapi.com"

class WebRepository : MovieRepository {
    companion object RetrofitServices {
        private val retrofit = Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val searchMovieApi: SearchMovieApi = retrofit.create(SearchMovieApi::class.java)
    }

    override suspend fun getMovie(title: String): Movie {
        return searchMovieApi.getMovie(title, API_KEY)
    }
}

interface SearchMovieApi {
    @GET("?")
    suspend fun getMovie(@Query("t") title: String, @Query("apikey") apiKey: String): Movie
}
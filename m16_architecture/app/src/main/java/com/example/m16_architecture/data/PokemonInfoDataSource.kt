package com.example.m16_architecture.data

import com.example.m16_architecture.entity.PokemonInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

private const val MOVIE_BASE_URl = "https://pokeapi.co"

class PokemonInfoDataSource @Inject constructor() {
    private fun randomSelectTwoRanges(): Int {
        val range1 = 1..1025
        val range2 = 10001..10277
        val selectedRange = if (Random.nextBoolean()) range1 else range2
        return Random.nextInt(selectedRange)
    }

    companion object RetrofitServices {
        private val retrofit = Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userDataApi: GetUserDataApi = retrofit.create(GetUserDataApi::class.java)
    }

    suspend fun loadPokemonInfo() = userDataApi.getData(randomSelectTwoRanges())
}

interface GetUserDataApi {
    @GET("/api/v2/pokemon/{id}/")
    suspend fun getData(@Path("id") id: Int): PokemonInfoDto
}

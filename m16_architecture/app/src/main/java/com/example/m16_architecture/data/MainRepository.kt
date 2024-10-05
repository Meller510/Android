package com.example.m16_architecture.data

import com.example.m16_architecture.entity.PokemonInfo
import kotlinx.coroutines.delay
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokemonInfoDataSource: PokemonInfoDataSource
) {
    suspend fun getPokemonInfo(): PokemonInfo {
        delay(500)
        return pokemonInfoDataSource.loadPokemonInfo()
    }
}
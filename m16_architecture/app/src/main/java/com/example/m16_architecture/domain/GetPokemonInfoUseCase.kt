package com.example.m16_architecture.domain

import com.example.m16_architecture.data.MainRepository
import com.example.m16_architecture.entity.PokemonInfo
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(): PokemonInfo {
        return mainRepository.getPokemonInfo()
    }
}
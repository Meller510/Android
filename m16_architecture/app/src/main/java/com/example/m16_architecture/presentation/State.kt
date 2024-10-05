package com.example.m16_architecture.presentation

import com.example.m16_architecture.entity.PokemonInfo
import java.lang.Error

sealed class State() {
    data object Loading : State()
    data object Init : State()
    data class Success(val result: PokemonInfo) : State()
    data class Error(val error: String) : State()

}
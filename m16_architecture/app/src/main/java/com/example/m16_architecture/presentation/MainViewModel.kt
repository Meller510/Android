package com.example.m16_architecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m16_architecture.domain.GetPokemonInfoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Init)
    val state = _state.asStateFlow()

    init {
        _state.value = State.Init
    }

    fun reloadPokemonInfo() {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                val pokemon = getPokemonInfoUseCase.execute()
                _state.value = State.Success(pokemon)
            } catch (t: Throwable) {
                _state.value = State.Error(t.message.toString())
            }
        }
    }
}

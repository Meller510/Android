package com.example.m14_retrofit.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Init)
    val state = _state.asStateFlow()

    private lateinit var user: User

    init {
        viewModelScope.launch { getDataUser() }
    }

    fun getDataUser() {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                user = repository.getUserData()
                _state.value = State.Success(user)
            } catch (t: Throwable) {
                _state.value = State.Error(t.message.toString())
            }
        }
    }

    fun stateToTextView(s: State): String {
        return when (s) {
            is State.Init, is State.Success -> s.user?.name.toString()
            is State.Loading -> "Загрузка данных..."
            is State.Error -> {
                Log.d("State.Error", "er: ${s.error} ")
                "Ошибка загрузки данных!"
            }
        }
    }

    fun stateIsLoading(state: State) = state == State.Loading
}

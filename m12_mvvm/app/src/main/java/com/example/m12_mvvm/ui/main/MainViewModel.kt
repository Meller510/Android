package com.example.m12_mvvm.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Init)
    val state = _state.asStateFlow()

    fun onSearchMovies(request: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val movie = repository.getMovie(request)
                if (movie.isEmpty()) _state.value = State.FailedRequest(request)
                else _state.value = State.Success(movie)
            } catch (t: Throwable) {
                _state.value = State.Error(t.message.toString())
            }
        }
    }

}
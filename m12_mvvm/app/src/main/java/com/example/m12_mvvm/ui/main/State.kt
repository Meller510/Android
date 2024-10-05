package com.example.m12_mvvm.ui.main

sealed class State {
    data object Init : State()
    data object Loading : State()
    data class Success(val result: Movie) : State()
    data class FailedRequest(val fRequest: String) : State()
    data class Error(val error: String) : State()
}
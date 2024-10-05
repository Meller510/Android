package com.example.m14_retrofit.ui.main

sealed class State(open val user: User? = null) {
    data object Loading : State()
    data object Init : State()
    data class Success(override val user: User) : State(user =  user)
    data class Error(val error: String) : State()

}


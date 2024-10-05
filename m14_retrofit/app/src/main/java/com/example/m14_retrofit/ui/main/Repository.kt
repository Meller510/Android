package com.example.m14_retrofit.ui.main

import kotlinx.coroutines.delay

interface MovieRepository {
    val repWeb: WebRepository
        get() = WebRepository()

    suspend fun getUserData(): User
}

class Repository : MovieRepository {
    override suspend fun getUserData(): User {
        delay(1000)
        return repWeb.getUserData()
    }
}

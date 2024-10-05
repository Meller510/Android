package com.example.m12_mvvm.ui.main

import kotlinx.coroutines.delay


interface MovieRepository {
    val repWeb: WebRepository
        get() = WebRepository()

    suspend fun getMovie(title: String): Movie?
}

class Repository : MovieRepository {
    override suspend fun getMovie(title: String): Movie {
        delay(3000)
        return repWeb.getMovie(title)
    }
}
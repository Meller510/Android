package com.example.m13_databinding.ui.main

import kotlinx.coroutines.delay


interface MovieRepository {
    val repWeb: WebRepository
        get() = WebRepository()

    suspend fun getMovie(title: String): Movie?
}

class Repository : MovieRepository {
    override suspend fun getMovie(title: String): Movie {
        delay(2000)
        return repWeb.getMovie(title)
    }
}
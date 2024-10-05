package com.example.m17_recyclerview.domain

import com.example.m17_recyclerview.data.Repository
import javax.inject.Inject

class GetMarsPhotoUseCase @Inject constructor(
    private val repository: Repository
) {
    fun apiService() = repository
}
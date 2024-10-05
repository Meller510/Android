package com.example.m17_recyclerview.presentation.photoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class PhotoListModelFactory @Inject constructor
    (private val photoListViewModel: PhotoListViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return photoListViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
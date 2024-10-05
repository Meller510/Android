package com.example.m13_databinding.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return modelClass.getConstructor(Repository::class.java)
                .newInstance(Repository())
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
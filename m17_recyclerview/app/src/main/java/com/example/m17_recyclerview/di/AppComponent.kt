package com.example.m17_recyclerview.di

import com.example.m17_recyclerview.presentation.photoList.PhotoListModelFactory
import dagger.Component

@Component
interface AppComponent {
    fun photoListModelFactory() : PhotoListModelFactory
}
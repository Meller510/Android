package com.example.m16_architecture.di;

import com.example.m16_architecture.presentation.MainViewModelFactory;

import dagger.Component;

@Component
interface AppComponent {
    fun mainViewModelFactory(): MainViewModelFactory
}


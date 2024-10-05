package com.example.m15_room

import android.app.Application
import androidx.room.Room

class App : Application() {
    var db: AppDataBase? = null

    override fun onCreate() {
        super.onCreate()
        if (db == null) {
            db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "db"
            ).build()
        }
    }
}
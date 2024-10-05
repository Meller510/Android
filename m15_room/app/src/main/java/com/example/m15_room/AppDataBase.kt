package com.example.m15_room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dictionaryDao() : DictionaryDao
}
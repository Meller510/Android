package com.example.m15_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int?= null,
    @ColumnInfo(name = "word")
    val word : String,
    @ColumnInfo(name = "counter")
    var counter : Int
)

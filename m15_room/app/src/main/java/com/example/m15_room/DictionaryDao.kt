package com.example.m15_room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary LIMIT :count")
    fun getLimitedEntries(count : Int = 5): Flow<List<Word>>

    @Query("SELECT COUNT(*) FROM dictionary")
    suspend fun getWordCount(): Int

    @Insert
    suspend fun insert(word: Word)

    @Query("DELETE FROM dictionary")
    suspend fun delete()

    @Update
    suspend fun update(word: Word)
}
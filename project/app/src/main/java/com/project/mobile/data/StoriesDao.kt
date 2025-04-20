package com.project.mobile.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Delete

import kotlinx.coroutines.flow.Flow

@Dao
interface StoriesDao {

    @Query("SELECT * FROM stories")
    fun getStories() : Flow<List<Routine>>

    @Query("SELECT * FROM stories WHERE id = :id")
    fun getRoutine(id: Int) : Routine?

    @Upsert
    suspend fun upsertRoutine(story: Routine)

    @Delete
    suspend fun deleteRoutine(story: Routine)
}
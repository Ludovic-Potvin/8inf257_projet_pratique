package com.project.mobile.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Delete

import kotlinx.coroutines.flow.Flow

@Dao
interface StoriesDao {

    @Query("SELECT * FROM stories")
    fun getStories() : Flow<List<Story>>

    @Query("SELECT * FROM stories WHERE id = :id")
    fun getStory(id: Int) : Story?

    @Upsert
    suspend fun upsertStory(story: Story)

    @Delete
    suspend fun deleteStory(story: Story)
}
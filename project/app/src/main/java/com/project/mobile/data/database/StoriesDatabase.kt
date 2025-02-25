package com.project.mobile.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.mobile.data.dao.StoriesDao
import com.project.mobile.data.model.Story

@Database(entities = [Story::class], version = 1)
abstract class StoriesDatabase : RoomDatabase() {

    abstract val dao: StoriesDao

    companion object {
        const val DATABASE_NAME = "stories.db"
    }
}
package com.project.mobile.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Routine::class], version = 1)
abstract class StoriesDatabase : RoomDatabase() {

    abstract val dao: StoriesDao

    companion object {
        const val DATABASE_NAME = "stories.db"
    }
}
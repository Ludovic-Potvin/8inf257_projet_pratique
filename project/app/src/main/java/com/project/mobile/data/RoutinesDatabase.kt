package com.project.mobile.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Routine::class], version = 1)
abstract class RoutinesDatabase : RoomDatabase() {

    abstract val dao: RoutinesDao

    companion object {
        const val DATABASE_NAME = "routines.db"
    }
}
package com.project.mobile.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Delete

import kotlinx.coroutines.flow.Flow

@Dao
interface RoutinesDao {

    @Query("SELECT * FROM routines")
    fun getRoutines() : Flow<List<Routine>>

    @Query("SELECT * FROM routines WHERE id = :id")
    fun getRoutine(id: Int) : Routine?

    @Upsert
    suspend fun upsertRoutine(routine: Routine)

    @Delete
    suspend fun deleteRoutine(routine: Routine)
}
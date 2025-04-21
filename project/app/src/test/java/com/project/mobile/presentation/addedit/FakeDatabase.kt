package com.project.mobile.presentation.addedit

import com.project.mobile.data.RoutinesDao
import com.project.mobile.data.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeDatabase : RoutinesDao {
    val routines = mutableListOf<Routine>()
    override fun getRoutines(): Flow<List<Routine>> = flow {
        emit(routines)
    }
    override fun getRoutine(id: Int): Routine? {
        return routines.find { it.id == id }
    }
    override suspend fun upsertRoutine(routine: Routine) {
        routines.removeIf { it.id == routine.id }
        routines.add(routine)
    }
    override suspend fun deleteRoutine(routine: Routine) {
        routines.remove(routine)
    }
}
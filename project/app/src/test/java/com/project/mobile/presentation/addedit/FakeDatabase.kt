package com.project.mobile.presentation.addedit

import com.project.mobile.data.StoriesDao
import com.project.mobile.data.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeDatabase : StoriesDao {
    val stories = mutableListOf<Routine>()
    override fun getStories(): Flow<List<Routine>> = flow {
        emit(stories)
    }
    override fun getRoutine(id: Int): Routine? {
        return stories.find { it.id == id }
    }
    override suspend fun upsertRoutine(story: Routine) {
        stories.removeIf { it.id == story.id }
        stories.add(story)
    }
    override suspend fun deleteRoutine(story: Routine) {
        stories.remove(story)
    }
}
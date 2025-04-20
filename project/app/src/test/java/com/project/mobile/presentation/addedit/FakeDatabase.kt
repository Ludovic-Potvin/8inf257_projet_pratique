package com.project.mobile.presentation.addedit

import com.project.mobile.data.StoriesDao
import com.project.mobile.data.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeDatabase : StoriesDao {
    val stories = mutableListOf<Story>()
    override fun getStories(): Flow<List<Story>> = flow {
        emit(stories)
    }
    override fun getStory(id: Int): Story? {
        return stories.find { it.id == id }
    }
    override suspend fun upsertStory(story: Story) {
        stories.removeIf { it.id == story.id }
        stories.add(story)
    }
    override suspend fun deleteStory(story: Story) {
        stories.remove(story)
    }
}
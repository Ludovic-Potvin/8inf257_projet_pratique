package com.project.mobile.presentation.list


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.mobile.presentation.StoryVM
import com.project.mobile.utils.deleteStoryFromList
import com.project.mobile.utils.getStories
import androidx.compose.runtime.State

class ListStoriesViewModel : ViewModel() {
    private val _stories: MutableState<List<StoryVM>> = mutableStateOf(emptyList())
    var stories: State<List<StoryVM>> = _stories

    init {
        _stories.value = loadStories()
    }

    private fun loadStories(): List<StoryVM> {
        return getStories()
    }

    fun onEvent(event: StoryEvent) {
        when(event) {
            is StoryEvent.Delete -> {
                deleteStory(event.story)
            }
        }
    }

    private fun deleteStory(story: StoryVM) {
        _stories.value = _stories.value.filter { it != story }
        deleteStoryFromList(story)
    }
}
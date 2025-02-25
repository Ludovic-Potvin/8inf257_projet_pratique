package com.project.mobile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.presentation.list.StoryEvent
import com.project.mobile.utils.DataStoreManager
import kotlinx.coroutines.launch

class ListStoriesViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _stories = mutableStateOf<List<StoryVM>>(emptyList()) // State pour lister les histoires
    val stories: State<List<StoryVM>> = _stories

    init {
        loadStories() // Charger les histoires dès le départ
    }

    init {
        loadStories()
    }

    // Charger les histoires depuis DataStore
    private fun loadStories() {
        viewModelScope.launch {
            dataStoreManager.storiesFlow.collect { storyList ->
                _stories.value = storyList
            }
        }
    }

    fun onEvent(event: StoryEvent) {
        when (event) {
            is StoryEvent.Delete -> {
                deleteStory(event.story)
            }
        }
    }

    private fun deleteStory(story: StoryVM) {
        viewModelScope.launch {
            dataStoreManager.deleteStory(story)
        }
    }
}

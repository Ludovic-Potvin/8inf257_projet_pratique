package com.project.mobile.presentation.addedit

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.data.StoriesDao
import com.project.mobile.notification.NotificationManager
import com.project.mobile.viewmodel.StoryVM
import com.project.mobile.weather.domain.GetWeeklyTemperaturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditStoryViewModel @Inject constructor(
    val dao: StoriesDao,
    savedStateHandle: SavedStateHandle,
    private val notificationManager: NotificationManager,
    private val getWeeklyTemperaturesUseCase: GetWeeklyTemperaturesUseCase
) : ViewModel()

{
    private val _story = mutableStateOf(StoryVM())
    val story : State<StoryVM> = _story

    private val _eventFlow = MutableSharedFlow<AddEditStoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val storyId = savedStateHandle.get<Int>("storyId")?: -1
        viewModelScope.launch(Dispatchers.IO) {
            val storyEntity = dao.getStory(storyId)
            _story.value = storyEntity?.let { StoryVM.fromEntity(it) } ?: StoryVM()
        }
    }

    fun onEvent(event: AddEditStoryEvent) {
        when (event) {
            is AddEditStoryEvent.EnteredTitle -> {
                _story.value = _story.value.copy(title = event.title)
            }

            is AddEditStoryEvent.EnteredDescription -> {
                _story.value = _story.value.copy(description = event.description)
            }

            is AddEditStoryEvent.EnteredCategory -> {
                _story.value = _story.value.copy(category = event.category)
            }

            is AddEditStoryEvent.EnteredHour -> {
                val newTime = event.hour.format(DateTimeFormatter.ofPattern("HH:mm"))
                _story.value = _story.value.copy(hour = newTime)
            }

            is AddEditStoryEvent.EnteredDay -> {
                _story.value = _story.value.copy(
                    days = _story.value.days.mapIndexed { i, value ->
                        if (i == event.index) !value else value
                    })
            }

            is AddEditStoryEvent.EnteredPriority -> {
                _story.value = _story.value.copy(priority = event.priority)
            }

            AddEditStoryEvent.SaveStory -> {
                saveStory()
            }

            AddEditStoryEvent.DeleteStory -> {
                deleteStory()
            }
        }
    }

    private fun saveStory() {
        viewModelScope.launch {
            try {
                if(story.value.title.isEmpty() || !story.value.days.any { it }) {
                    _eventFlow.emit(AddEditStoryUiEvent.ShowMessage("Unable to save story"))
                }
                else {
                    val entity = story.value.toEntity()
                    notificationManager.setNotification(entity)
                    dao.upsertStory(entity)
                    _eventFlow.emit(AddEditStoryUiEvent.SavedStory)
                }
            } catch(e: Exception) {
                _eventFlow.emit(AddEditStoryUiEvent.ShowMessage(e.message!!))
            }
        }
    }

    private fun deleteStory() {
        viewModelScope.launch {
            try {
                val entity = story.value.toEntity()
                notificationManager.cancelNotification(entity)
                dao.deleteStory(entity)
                _eventFlow.emit(AddEditStoryUiEvent.DeletedStory)
            } catch(e: Exception) {
                _eventFlow.emit(AddEditStoryUiEvent.ShowMessage(e.message!!))
            }
        }
    }

    var temperatures = mutableStateOf<List<Double?>>(emptyList())



    init {
        loadTemperature()
    }

    private fun loadTemperature() {
        viewModelScope.launch {
            try {
                temperatures.value = getWeeklyTemperaturesUseCase("Chicoutimi")
            } catch (e: Exception) {
                e.printStackTrace()
                temperatures.value = List(7) { null }
            }
        }
    }




}


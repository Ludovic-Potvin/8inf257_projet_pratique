package com.project.mobile.presentation.addedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.data.StoriesDao
import com.project.mobile.notification.cancelNotification
import com.project.mobile.notification.scheduleNotification
import com.project.mobile.viewmodel.StoryVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import android.content.Context
import com.project.mobile.notification.checkAndRequestPermissions

class AddEditStoryViewModel(val dao: StoriesDao, storyId: Int = -1) : ViewModel() {
    private val _story = mutableStateOf(StoryVM())
    val story : State<StoryVM> = _story

    private val _eventFlow = MutableSharedFlow<AddEditStoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val storyEntity = dao.getStory(storyId)
            _story.value = storyEntity?.let { StoryVM.fromEntity(it) } ?: StoryVM()
        }
    }

    fun onEvent(context :Context, event: AddEditStoryEvent) {
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
                saveStory(context)
            }

            AddEditStoryEvent.DeleteStory -> {
                deleteStory(context)
            }
        }
    }

    private fun saveStory(context :Context) {
        viewModelScope.launch {
            try {
                val entity = story.value.toEntity()
                dao.upsertStory(entity)
                _eventFlow.emit(AddEditStoryUiEvent.SavedStory)
                scheduleNotification(context)
            } catch(e: Exception) {
                _eventFlow.emit(AddEditStoryUiEvent.ShowMessage(e.message!!))
            }
        }
    }

    private fun deleteStory(context :Context) {
        viewModelScope.launch {
            try {
                val entity = story.value.toEntity()
                dao.deleteStory(entity)
                _eventFlow.emit(AddEditStoryUiEvent.DeletedStory)
                cancelNotification(context)
            } catch(e: Exception) {
                _eventFlow.emit(AddEditStoryUiEvent.ShowMessage(e.message!!))
            }
        }
    }

    private fun scheduleNotification(context :Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val storyData = story.value
            val days = listOf("Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi")

            checkAndRequestPermissions(context)
            storyData.days.forEachIndexed { index, isActive ->
                if (isActive) {
                    scheduleNotification(
                        context = context,
                        jour = days[index],
                        hour = storyData.getHourAsLocalTime().hour,
                        minute = storyData.getHourAsLocalTime().minute,
                        titreNotif = storyData.title,
                        messageNotif = storyData.description,
                        priorite = storyData.priority
                    )
                }
            }
        }
    }

    private fun cancelNotification(context :Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val storyData = story.value
            val days = listOf("Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi")

            storyData.days.forEachIndexed { index, isActive ->
                if (isActive) {
                    cancelNotification(
                        context = context,
                        jour = days[index],
                        hour = storyData.getHourAsLocalTime().hour,
                        minute = storyData.getHourAsLocalTime().minute
                    )
                }
            }
        }
    }
}


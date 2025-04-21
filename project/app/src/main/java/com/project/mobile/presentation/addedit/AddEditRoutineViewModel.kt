package com.project.mobile.presentation.addedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.data.RoutinesDao
import com.project.mobile.notification.NotificationManager
import com.project.mobile.viewmodel.RoutineVM
import com.project.mobile.weather.domain.GetWeeklyTemperaturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddEditRoutineViewModel @Inject constructor(
    val dao: RoutinesDao,
    savedStateHandle: SavedStateHandle,
    private val notificationManager: NotificationManager,
    private val getWeeklyTemperaturesUseCase: GetWeeklyTemperaturesUseCase
) : ViewModel()

{
    private val _routine = mutableStateOf(RoutineVM())
    val routine : State<RoutineVM> = _routine

    private val _eventFlow = MutableSharedFlow<AddEditRoutineUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val routineId = savedStateHandle.get<Int>("routineId")?: -1
        viewModelScope.launch(Dispatchers.IO) {
            val routineEntity = dao.getRoutine(routineId)
            _routine.value = routineEntity?.let { RoutineVM.fromEntity(it) } ?: RoutineVM()
        }
    }

    fun onEvent(event: AddEditRoutineEvent) {
        when (event) {
            is AddEditRoutineEvent.EnteredTitle -> {
                _routine.value = _routine.value.copy(title = event.title)
            }

            is AddEditRoutineEvent.EnteredDescription -> {
                _routine.value = _routine.value.copy(description = event.description)
            }

            is AddEditRoutineEvent.EnteredCategory -> {
                _routine.value = _routine.value.copy(category = event.category)
            }

            is AddEditRoutineEvent.EnteredHour -> {
                val newTime = event.hour.format(DateTimeFormatter.ofPattern("HH:mm"))
                _routine.value = _routine.value.copy(hour = newTime)
            }

            is AddEditRoutineEvent.EnteredDay -> {
                _routine.value = _routine.value.copy(
                    days = _routine.value.days.mapIndexed { i, value ->
                        if (i == event.index) !value else value
                    })
            }

            is AddEditRoutineEvent.EnteredPriority -> {
                _routine.value = _routine.value.copy(priority = event.priority)
            }

            AddEditRoutineEvent.SaveRoutine -> {
                saveRoutine()
            }

            AddEditRoutineEvent.DeleteRoutine -> {
                deleteRoutine()
            }
        }
    }

    private fun saveRoutine() {
        viewModelScope.launch {
            try {
                if(routine.value.title.isEmpty() || !routine.value.days.any { it }) {
                    _eventFlow.emit(AddEditRoutineUiEvent.ShowMessage("Unable to save routine"))
                }
                else {
                    val entity = routine.value.toEntity()
                    notificationManager.setNotification(entity)
                    dao.upsertRoutine(entity)
                    _eventFlow.emit(AddEditRoutineUiEvent.SavedRoutine)
                }
            } catch(e: Exception) {
                _eventFlow.emit(AddEditRoutineUiEvent.ShowMessage(e.message!!))
            }
        }
    }

    private fun deleteRoutine() {
        viewModelScope.launch {
            try {
                val entity = routine.value.toEntity()
                notificationManager.cancelNotification(entity)
                dao.deleteRoutine(entity)
                _eventFlow.emit(AddEditRoutineUiEvent.DeletedRoutine)
            } catch(e: Exception) {
                _eventFlow.emit(AddEditRoutineUiEvent.ShowMessage(e.message!!))
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


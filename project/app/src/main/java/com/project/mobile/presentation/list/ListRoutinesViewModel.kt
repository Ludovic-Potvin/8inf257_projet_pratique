package com.project.mobile.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.data.RoutinesDao
import com.project.mobile.viewmodel.RoutineVM
import com.project.mobile.weather.domain.GetWeeklyTemperaturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListRoutinesViewModel @Inject constructor(
    val dao: RoutinesDao,
    private val getWeeklyTemperaturesUseCase: GetWeeklyTemperaturesUseCase
) : ViewModel() {

    private val _routines: MutableState<List<RoutineVM>> = mutableStateOf(emptyList())
    val routines: State<List<RoutineVM>> = _routines
    var job: Job? = null
    var temperatures = mutableStateOf<List<Double?>>(emptyList())



    init {
        loadRoutines()
        loadTemperature()
    }

    private fun loadRoutines() {
        job?.cancel()

        job = dao.getRoutines().onEach { routines ->
            _routines.value = routines.map {
                RoutineVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
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

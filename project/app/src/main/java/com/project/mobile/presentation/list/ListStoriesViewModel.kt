package com.project.mobile.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.api.WeatherRepository
import com.project.mobile.data.StoriesDao
import com.project.mobile.viewmodel.StoryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListStoriesViewModel @Inject constructor(
    val dao: StoriesDao
) : ViewModel() {

    private val _stories: MutableState<List<StoryVM>> = mutableStateOf(emptyList())
    val stories: State<List<StoryVM>> = _stories
    var job: Job? = null

    private val weatherRepository = WeatherRepository()

    // Liste des températures pour les 7 jours
    var temperatures = mutableStateOf<List<Double>>(emptyList())

    init {
        loadStories()
        loadTemperature()
    }

    private fun loadStories() {
        job?.cancel()

        job = dao.getStories().onEach { stories ->
            _stories.value = stories.map {
                StoryVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadTemperature() {
        viewModelScope.launch {
            try {
                val result = weatherRepository.getWeeklyTemperatures("Chicoutimi")

                println("Nombre de températures récupérées : ${result.size}")

                temperatures.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

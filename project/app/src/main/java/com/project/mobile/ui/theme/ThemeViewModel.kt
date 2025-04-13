package com.project.mobile.ui.theme;

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mobile.preferences.PreferenceManager;
import com.project.mobile.presentation.addedit.AddEditStoryEvent
import com.project.mobile.presentation.preference.PreferenceEvent

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private val _theme: MutableState<Int> = mutableIntStateOf(0)

    val theme: State<Int> = _theme

    init {
        viewModelScope.launch {
            val result = preferenceManager.getTheme()
            result.getOrNull()?.let { _theme.value = it }
        }
    }

    fun onEvent(event: PreferenceEvent) {
        when (event) {
            is PreferenceEvent.EnteredTheme -> {
                viewModelScope.launch {
                    preferenceManager.setTheme(event.theme)
                    _theme.value = event.theme
                }
            }
        }
    }
}

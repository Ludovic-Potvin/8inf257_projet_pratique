package com.project.mobile.presentation.addedit

sealed interface AddEditRoutineUiEvent {
    data class ShowMessage(val message: String) : AddEditRoutineUiEvent
    data object SavedRoutine : AddEditRoutineUiEvent
    data object DeletedRoutine : AddEditRoutineUiEvent
}
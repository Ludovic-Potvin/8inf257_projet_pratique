package com.project.mobile.presentation.addedit

sealed interface AddEditStoryUiEvent {
    data class ShowMessage(val message: String) : AddEditStoryUiEvent
    data object SavedStory : AddEditStoryUiEvent
    data object DeletedStory : AddEditStoryUiEvent
}
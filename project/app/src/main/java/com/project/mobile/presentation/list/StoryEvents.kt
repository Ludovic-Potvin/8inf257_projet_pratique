package com.project.mobile.presentation.list


import com.project.mobile.presentation.StoryVM

sealed class StoryEvent {
    data class Delete(val story: StoryVM) : StoryEvent()
}
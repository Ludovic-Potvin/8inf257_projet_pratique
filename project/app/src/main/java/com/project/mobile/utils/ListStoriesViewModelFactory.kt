package com.project.mobile.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.mobile.viewmodel.ListStoriesViewModel

class ListStoriesViewModelFactory(
    private val dataStoreManager: DataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStoriesViewModel::class.java)) {
            return ListStoriesViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

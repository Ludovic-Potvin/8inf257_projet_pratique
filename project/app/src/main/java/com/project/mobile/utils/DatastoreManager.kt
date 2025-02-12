package com.project.mobile.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mobile.presentation.StoryVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Extension DataStore
val Context.dataStore by preferencesDataStore(name = "story_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val STORY_KEY = stringPreferencesKey("stories")
        private val gson = Gson()
    }

    // Récupérer la liste des histoires depuis DataStore
    val storiesFlow: Flow<List<StoryVM>> = context.dataStore.data.map { preferences ->
        val jsonString = preferences[STORY_KEY] ?: "[]"
        val listType = object : TypeToken<List<StoryVM>>() {}.type
        gson.fromJson(jsonString, listType) ?: emptyList()
    }

    // Sauvegarder la liste des histoires dans DataStore
    suspend fun saveStories(stories: List<StoryVM>) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                val jsonString = gson.toJson(stories)
                preferences[STORY_KEY] = jsonString
            }
        }
    }

    // Ajouter ou mettre à jour une histoire
    suspend fun addOrUpdateStory(story: StoryVM) {
        val currentStories = storiesFlow.first().toMutableList()
        val existingIndex = currentStories.indexOfFirst { it.id == story.id }

        if (existingIndex != -1) {
            currentStories[existingIndex] = story // Mise à jour
        } else {
            currentStories.add(story) // Ajout
        }

        saveStories(currentStories)
    }

    // Supprimer une histoire
    suspend fun deleteStory(story: StoryVM) {
        val currentStories = storiesFlow.first().toMutableList()
        currentStories.removeIf { it.id == story.id }
        saveStories(currentStories)
    }
}

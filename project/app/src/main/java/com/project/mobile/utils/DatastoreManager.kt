package com.project.mobile.utils

import android.content.Context
import android.util.Log
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
import java.time.format.DateTimeFormatter

// Extension DataStore
val Context.dataStore by preferencesDataStore(name = "story_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val STORY_KEY = stringPreferencesKey("stories")
        private val LAST_STORY_ID_KEY = intPreferencesKey("last_story_id")
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

    // Ajouter ou mettre à jour une histoire dans DataStore
    suspend fun addOrUpdateStory(story: StoryVM) {
        Log.d("addOrUpdateStory", "story=$story")
        // Utilisation de 'first' pour récupérer les histoires actuelles
        val currentStories = storiesFlow.first().toMutableList()

        // Vérifier si l'histoire existe déjà et mettre à jour
        val existingIndex = currentStories.indexOfFirst { it.id == story.id }

        if (existingIndex != -1) {
            // Histoire existante, mise à jour
            currentStories[existingIndex] = story
        } else {
            // Ajouter une nouvelle histoire
            currentStories.add(story)
        }

        // Sauvegarder la liste mise à jour dans DataStore
        saveStories(currentStories)
    }


    // Supprimer une histoire
    suspend fun deleteStory(story: StoryVM) {
        val currentStories = storiesFlow.first().toMutableList()
        currentStories.removeIf { it.id == story.id }
        saveStories(currentStories)
    }


    suspend fun getStoryById(id: Int): StoryVM? {
        val stories = storiesFlow.first()
        val story = stories.find { it.id == id }

        return story?.copy(hour = story.getHourAsLocalTime().format( DateTimeFormatter.ofPattern("HH:mm")))
    }

    private suspend fun getLastStoryId(): Int {
        return context.dataStore.data.map { preferences ->
            preferences[LAST_STORY_ID_KEY] ?: 0 // Si aucun ID n'existe, commence à 0
        }.first()
    }

    private suspend fun saveLastStoryId(newId: Int) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[LAST_STORY_ID_KEY] = newId
            }
        }
    }

    suspend fun generateNewStoryId(): Int {
        val newId = getLastStoryId() + 1
        saveLastStoryId(newId) // Mise à jour dans DataStore
        return newId
    }

    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear() // Supprime toutes les valeurs enregistrées
        }
    }
}
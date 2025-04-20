package com.project.mobile.presentation.addedit


import androidx.lifecycle.SavedStateHandle

import app.cash.turbine.test

import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.jupiter.api.Assertions.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertTrue
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.mobile.weather.data.WeatherApiService
import com.project.mobile.weather.data.WeatherRepository
import com.project.mobile.weather.domain.GetWeeklyTemperaturesUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditRoutineViewModelTestAjoutSupression {


    private val context = mockk<Context>(relaxed = true)
    private val builder = mockk<NotificationCompat.Builder>(relaxed = true)
    private val notificationManagerCompact = mockk<NotificationManagerCompat>(relaxed = true)
    private val notificationManager = FakeNotificationManager(context, builder, notificationManagerCompact)
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `ajout et suppression d'une routine avec donnees valides`() = runTest {

        val dao = FakeDatabase()
        val handle = SavedStateHandle(mapOf("storyId" to 1))
        val weeklytempusecase = GetWeeklyTemperaturesUseCase(WeatherRepository(
            Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)))
        val viewModel = AddEditRoutineViewModel(dao, handle, notificationManager, weeklytempusecase)

        // pour attendre la fin du init
        advanceUntilIdle()

        // When
        viewModel.onEvent(AddEditRoutineEvent.EnteredTitle("My Routine"))
        viewModel.onEvent(AddEditRoutineEvent.EnteredDay(0))

        viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)

        viewModel.eventFlow.test {
            val saveEvent = awaitItem()
            assert(saveEvent is AddEditRoutineUiEvent.SavedRoutine)
            assertTrue(dao.stories.size == 1)
            assertTrue(notificationManager.addedNotification.size == 1)
            viewModel.onEvent(AddEditRoutineEvent.DeleteRoutine)
            val deleteEvent = awaitItem()
            assert(deleteEvent is AddEditRoutineUiEvent.DeletedRoutine)
            assertTrue(dao.stories.size == 0)
            assertTrue(notificationManager.addedNotification.size == 0)

            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `tentative d'ajout de routine sans jour selectionnes qui doit echouer`() = runTest {
        val context = mockk<Context>(relaxed = true)
        val builder = mockk<NotificationCompat.Builder>(relaxed = true)
        val notificationManagerCompact = mockk<NotificationManagerCompat>(relaxed = true)
        val notificationManager = FakeNotificationManager(context, builder, notificationManagerCompact)
        val dao = FakeDatabase()
        val handle = SavedStateHandle(mapOf("storyId" to -1))
        val weeklytempusecase = GetWeeklyTemperaturesUseCase(WeatherRepository(
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)))
        val viewModel = AddEditRoutineViewModel(dao, handle, notificationManager, weeklytempusecase)


        advanceUntilIdle()


        viewModel.onEvent(AddEditRoutineEvent.EnteredTitle("My Routine"))

        viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)


        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditRoutineUiEvent.ShowMessage)
            assertEquals("Unable to save story", (event as AddEditRoutineUiEvent.ShowMessage).message)
            assertTrue(dao.stories.isEmpty())
            assertTrue(notificationManager.addedNotification.isEmpty())
            cancelAndIgnoreRemainingEvents()

        }

    }

    @Test
    fun `tentative d'ajout de routine sans titre qui doit echouer`() = runTest {
        val dao = FakeDatabase()
        val handle = SavedStateHandle(mapOf("storyId" to -1))
        val weeklytempusecase = GetWeeklyTemperaturesUseCase(WeatherRepository(
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)))
        val viewModel = AddEditRoutineViewModel(dao, handle, notificationManager, weeklytempusecase)


        advanceUntilIdle()


        viewModel.onEvent(AddEditRoutineEvent.EnteredDay(0))

        viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)


        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditRoutineUiEvent.ShowMessage)
            assertEquals("Unable to save story", (event as AddEditRoutineUiEvent.ShowMessage).message)
            assertTrue(dao.stories.size == 0)
            assertTrue(notificationManager.addedNotification.size == 0)
            cancelAndIgnoreRemainingEvents()

        }

    }
}




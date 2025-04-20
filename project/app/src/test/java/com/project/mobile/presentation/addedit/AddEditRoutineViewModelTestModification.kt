package com.project.mobile.presentation.addedit

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.project.mobile.data.Routine
import com.project.mobile.weather.data.WeatherApiService
import com.project.mobile.weather.data.WeatherRepository
import com.project.mobile.weather.domain.GetWeeklyTemperaturesUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@OptIn(ExperimentalCoroutinesApi::class)
class AddEditRoutineViewModelTestModification {


    private val context = mockk<Context>(relaxed = true)
    private val builder = mockk<NotificationCompat.Builder>(relaxed = true)
    private val notificationManagerCompact = mockk<NotificationManagerCompat>(relaxed = true)
    private val notificationManager = FakeNotificationManager(context, builder, notificationManagerCompact)
    private val dao = FakeDatabase()
    private val dispatcher = StandardTestDispatcher()
    private val weeklytempusecase = GetWeeklyTemperaturesUseCase(WeatherRepository(Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)))

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `modification routine`() = runTest {
        val story = Routine(
            id = 1,
            title = "Ancien titre",
            description = "",
            category = "",
            hour = "",
            days = "1000000",
            priority = 1
        )
        dao.stories.add(story)

        val handle = SavedStateHandle(mapOf("storyId" to 1))
        val viewModel = AddEditRoutineViewModel(dao, handle, notificationManager, weeklytempusecase)

        advanceUntilIdle()

        viewModel.onEvent(AddEditRoutineEvent.EnteredTitle("Nouveau titre"))
        advanceUntilIdle()

        viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)
        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditRoutineUiEvent.SavedRoutine)

            val modifiedRoutine = dao.stories.first()
            assertEquals("Nouveau titre", modifiedRoutine.title)
            assertEquals(1, dao.stories.size)

            assertEquals("Nouveau titre", notificationManager.addedNotification.first().title)
            assertEquals(1, notificationManager.addedNotification.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

}



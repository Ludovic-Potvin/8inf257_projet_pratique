package com.project.mobile.presentation.addedit


import androidx.lifecycle.SavedStateHandle

import app.cash.turbine.test

import com.project.mobile.notification.NotificationManager


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

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditStoryViewModelTest {

    private val dao = FakeDatabase()
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
    fun `ajout et supression d'une stoty avec des donnees valides`() = runTest {

        val handle = SavedStateHandle(mapOf("storyId" to -1))
        val viewModel = AddEditStoryViewModel(dao, handle, notificationManager)

        // pour attendre la fin du init
        advanceUntilIdle()

        // When
        viewModel.onEvent(AddEditStoryEvent.EnteredTitle("My Story"))
        viewModel.onEvent(AddEditStoryEvent.EnteredDay(0))
        viewModel.onEvent(AddEditStoryEvent.SaveStory)

        // Then
        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditStoryUiEvent.SavedStory)
            assertTrue(dao.stories.size == 1)
            assertTrue(notificationManager.addedNotification.size == 1)
        }


        viewModel.onEvent(AddEditStoryEvent.DeleteStory)

        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditStoryUiEvent.DeletedStory)
            assertTrue(dao.stories.size == 0)
            assertTrue(notificationManager.addedNotification.size == 0)
        }
    }

    @Test
    fun `tentative d'ajout de story sans jour selectionnes qui doit echouer`() = runTest {

        val handle = SavedStateHandle(mapOf("storyId" to -1))
        val viewModel = AddEditStoryViewModel(dao, handle, notificationManager)


        advanceUntilIdle()


        viewModel.onEvent(AddEditStoryEvent.EnteredTitle("My Story"))

        viewModel.onEvent(AddEditStoryEvent.SaveStory)


        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditStoryUiEvent.ShowMessage)
            assertEquals("Unable to save story", (event as AddEditStoryUiEvent.ShowMessage).message)
            assertTrue(dao.stories.size == 0)
            assertTrue(notificationManager.addedNotification.size == 0)
            cancelAndIgnoreRemainingEvents()

        }

    }

    @Test
    fun `tentative d'ajout de story sans titre qui doit echouer`() = runTest {

        val handle = SavedStateHandle(mapOf("storyId" to -1))
        val viewModel = AddEditStoryViewModel(dao, handle, notificationManager)


        advanceUntilIdle()


        viewModel.onEvent(AddEditStoryEvent.EnteredDay(0))

        viewModel.onEvent(AddEditStoryEvent.SaveStory)


        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditStoryUiEvent.ShowMessage)
            assertEquals("Unable to save story", (event as AddEditStoryUiEvent.ShowMessage).message)
            assertTrue(dao.stories.size == 0)
            assertTrue(notificationManager.addedNotification.size == 0)
            cancelAndIgnoreRemainingEvents()

        }

    }
}

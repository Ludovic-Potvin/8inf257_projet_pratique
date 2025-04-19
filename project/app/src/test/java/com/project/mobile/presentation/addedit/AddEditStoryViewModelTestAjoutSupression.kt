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

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditStoryViewModelTestAjoutSupression {


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
        val viewModel = AddEditStoryViewModel(dao, handle, notificationManager)

        // pour attendre la fin du init
        advanceUntilIdle()

        // When
        viewModel.onEvent(AddEditStoryEvent.EnteredTitle("My Story"))
        viewModel.onEvent(AddEditStoryEvent.EnteredDay(0))

        viewModel.onEvent(AddEditStoryEvent.SaveStory)

        viewModel.eventFlow.test {
            val saveEvent = awaitItem()
            assert(saveEvent is AddEditStoryUiEvent.SavedStory)
            assertTrue(dao.stories.size == 1)
            assertTrue(notificationManager.addedNotification.size == 1)
            viewModel.onEvent(AddEditStoryEvent.DeleteStory)
            val deleteEvent = awaitItem()
            assert(deleteEvent is AddEditStoryUiEvent.DeletedStory)
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
        val viewModel = AddEditStoryViewModel(dao, handle, notificationManager)


        advanceUntilIdle()


        viewModel.onEvent(AddEditStoryEvent.EnteredTitle("My Story"))

        viewModel.onEvent(AddEditStoryEvent.SaveStory)


        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditStoryUiEvent.ShowMessage)
            assertEquals("Unable to save story", (event as AddEditStoryUiEvent.ShowMessage).message)
            assertTrue(dao.stories.isEmpty())
            assertTrue(notificationManager.addedNotification.isEmpty())
            cancelAndIgnoreRemainingEvents()

        }

    }

    @Test
    fun `tentative d'ajout de routine sans titre qui doit echouer`() = runTest {
        val dao = FakeDatabase()
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




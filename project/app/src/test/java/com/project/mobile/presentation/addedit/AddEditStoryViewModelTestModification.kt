package com.project.mobile.presentation.addedit

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.project.mobile.data.Story
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
import org.junit.jupiter.api.Assertions.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class AddEditStoryViewModelTestModification {


    private val context = mockk<Context>(relaxed = true)
    private val builder = mockk<NotificationCompat.Builder>(relaxed = true)
    private val notificationManagerCompact = mockk<NotificationManagerCompat>(relaxed = true)
    private val notificationManager = FakeNotificationManager(context, builder, notificationManagerCompact)
    private val dao = FakeDatabase()
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
    fun `modification routine`() = runTest {
        val story = Story(
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
        val viewModel = AddEditStoryViewModel(dao, handle, notificationManager)

        advanceUntilIdle()

        viewModel.onEvent(AddEditStoryEvent.EnteredTitle("Nouveau titre"))
        advanceUntilIdle()
        println("Histoire modifiée avant SaveStory: ${viewModel.story.value.title}")

        viewModel.onEvent(AddEditStoryEvent.SaveStory)
        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is AddEditStoryUiEvent.SavedStory)
            val modifiedStory = dao.stories.first()
            assertEquals("Nouveau titre", modifiedStory.title)
            assertEquals(1, dao.stories.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

}



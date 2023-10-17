package com.majelan.androidtechnicaltest.ui.scenes.player

import com.google.common.truth.Truth.assertThat
import com.majelan.androidtechnicaltest.MainCoroutineRule
import com.majelan.androidtechnicaltest.observe
import com.majelan.androidtechnicaltest.player.PlayerManager
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class PlayerViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val playerState = PlayerManager.State()
    private val mutableStateFlow = MutableStateFlow(playerState)
    private val playerManager = mockk<PlayerManager>(relaxUnitFun = true) {
        every { state } returns mutableStateFlow
    }

    @Test
    fun `should play player on action Play`() = runTest {

        // Given
        val viewModel = PlayerViewModel(playerManager)

        // When
        viewModel.handle(PlayerViewModel.Action.Play)

        // Then
        advanceUntilIdle()
        coVerify { playerManager.play() }
    }

    @Test
    fun `should pause player on action Pause`() = runTest {

        // Given
        val viewModel = PlayerViewModel(playerManager)

        // When
        viewModel.handle(PlayerViewModel.Action.Pause)

        // Then
        advanceUntilIdle()
        coVerify { playerManager.pause() }
    }

    @Test
    fun `should skip next player on action Next`() = runTest {

        // Given
        val viewModel = PlayerViewModel(playerManager)

        // When
        viewModel.handle(PlayerViewModel.Action.Next)

        // Then
        advanceUntilIdle()
        coVerify { playerManager.next() }
    }

    @Test
    fun `should skip previous player on action Previous`() = runTest {

        // Given
        val viewModel = PlayerViewModel(playerManager)

        // When
        viewModel.handle(PlayerViewModel.Action.Previous)

        // Then
        advanceUntilIdle()
        coVerify { playerManager.previous() }
    }

    @Test
    fun `should seek player on action Seek`() = runTest {

        // Given
        val viewModel = PlayerViewModel(playerManager)

        // When
        viewModel.handle(PlayerViewModel.Action.Seek(.5f))

        // Then
        advanceUntilIdle()
        coVerify { playerManager.seek(.5f) }
    }

    @Test
    fun `should update state when playerManager dispatch a new state`() = runTest {

        // Given
        val initialState = PlayerManager.State()
        val viewModel = PlayerViewModel(playerManager)

        // When
        val resultState = viewModel.state.observe(this)
        mutableStateFlow.value = initialState.copy(isPlaying = true)

        // Then
        advanceUntilIdle()
        assertThat(resultState.values)
            .containsExactly(
                PlayerViewModel.State(),
                PlayerViewModel.State(isPlaying = true)
            )
            .inOrder()

        resultState.finish()
    }
}
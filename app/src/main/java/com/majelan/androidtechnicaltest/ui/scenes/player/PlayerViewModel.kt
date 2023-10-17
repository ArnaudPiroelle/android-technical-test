package com.majelan.androidtechnicaltest.ui.scenes.player

import androidx.lifecycle.viewModelScope
import com.majelan.androidtechnicaltest.player.PlayerManager
import com.majelan.androidtechnicaltest.ui.architecture.BaseAction
import com.majelan.androidtechnicaltest.ui.architecture.BaseState
import com.majelan.androidtechnicaltest.ui.architecture.BaseViewModel
import com.majelan.androidtechnicaltest.ui.architecture.NoSideEffect
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PlayerViewModel(
    private val playerManager: PlayerManager,
) : BaseViewModel<PlayerViewModel.Action, PlayerViewModel.State, NoSideEffect>() {

    override val state: StateFlow<State> = playerManager.state
        .map {
            State(
                isPlaying = it.isPlaying,
                progress = it.progress,
                mediaTitle = it.mediaMetadata?.title?.toString() ?: "",
                mediaArtist = it.mediaMetadata?.artist?.toString() ?: "",
                mediaThumbnail = it.mediaMetadata?.artworkUri?.toString(),
                currentPosition = it.currentPosition,
                totalDuration = it.totalDuration,
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    override suspend fun onHandle(action: Action) = when (action) {
        is Action.Next -> playerManager.next()
        is Action.Pause -> playerManager.pause()
        is Action.Play -> playerManager.play()
        is Action.Previous -> playerManager.previous()
        is Action.Seek -> playerManager.seek(action.position)
    }

    sealed class Action : BaseAction {
        data class Seek(val position: Float) : Action()
        data object Play : Action()
        data object Pause : Action()
        data object Next : Action()
        data object Previous : Action()
    }

    data class State(
        val isPlaying: Boolean = false,
        val progress: Float = 0f,
        val mediaTitle: String = "",
        val mediaArtist: String = "",
        val mediaThumbnail: String? = null,
        val currentPosition: Long = 0,
        val totalDuration: Long = 0,
    ) : BaseState

}
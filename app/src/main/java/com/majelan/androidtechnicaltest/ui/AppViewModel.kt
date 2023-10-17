package com.majelan.androidtechnicaltest.ui

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
class AppViewModel(
    private val playerManager: PlayerManager
) : BaseViewModel<AppViewModel.Action, AppViewModel.State, NoSideEffect>() {
    override val state: StateFlow<State>
        get() = playerManager.state.map {
            val mediaThumbnail = it.mediaMetadata?.artworkUri.toString()
            State(
                isPlaying = it.isPlaying,
                progress = it.progress,
                displayMiniPlayer = it.hasTracks,
                mediaTitle = it.mediaMetadata?.title?.toString() ?: "",
                mediaArtist = it.mediaMetadata?.artist?.toString() ?: "",
                mediaThumbnail = mediaThumbnail
            )

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    override suspend fun onHandle(action: Action) = when (action) {
        is Action.Pause -> playerManager.pause()
        is Action.Play -> playerManager.play()
    }

    sealed class Action : BaseAction {
        data object Play : Action()
        data object Pause : Action()
    }

    data class State(
        val isPlaying: Boolean = false,
        val displayMiniPlayer: Boolean = false,
        val progress: Float = 0f,
        val mediaTitle: String = "",
        val mediaArtist: String = "",
        val mediaThumbnail: String? = null
    ) : BaseState
}
package com.majelan.androidtechnicaltest.ui.scenes.home

import com.majelan.androidtechnicaltest.domain.interactor.GetAlbumDetails
import com.majelan.androidtechnicaltest.domain.interactor.GetAlbums
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.player.PlayerManager
import com.majelan.androidtechnicaltest.ui.architecture.BaseAction
import com.majelan.androidtechnicaltest.ui.architecture.BaseState
import com.majelan.androidtechnicaltest.ui.architecture.BaseViewModel2
import com.majelan.androidtechnicaltest.ui.architecture.NoSideEffect
import com.majelan.androidtechnicaltest.ui.scenes.home.HomeViewModel.Action
import com.majelan.androidtechnicaltest.ui.scenes.home.HomeViewModel.State
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getAlbums: GetAlbums,
    private val getAlbumDetails: GetAlbumDetails,
    private val playerManager: PlayerManager,
) : BaseViewModel2<Action, State, NoSideEffect>(initialState = State()) {

    override suspend fun onHandle(action: Action) = when (action) {
        is Action.LoadCatalog -> loadCatalog()
        is Action.PlayAlbum -> playAlbum(action.albumId)
    }

    private suspend fun playAlbum(albumId: String) {
        val albumDetails = getAlbumDetails(GetAlbumDetails.Params(albumId))
        playerManager.playTracks(albumDetails.tracks)
    }

    private suspend fun loadCatalog() {
        val albums = getAlbums()
        updateState { state -> state.copy(albums = albums) }
    }

    sealed class Action : BaseAction {
        data object LoadCatalog : Action()
        data class PlayAlbum(val albumId: String) : Action()
    }

    data class State(val albums: List<Album> = emptyList()) : BaseState
}
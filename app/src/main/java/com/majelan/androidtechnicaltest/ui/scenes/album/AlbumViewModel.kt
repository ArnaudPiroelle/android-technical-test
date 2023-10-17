package com.majelan.androidtechnicaltest.ui.scenes.album

import com.majelan.androidtechnicaltest.domain.interactor.GetAlbumDetails
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.Track
import com.majelan.androidtechnicaltest.player.PlayerManager
import com.majelan.androidtechnicaltest.ui.architecture.BaseAction
import com.majelan.androidtechnicaltest.ui.architecture.BaseState
import com.majelan.androidtechnicaltest.ui.architecture.BaseViewModel2
import com.majelan.androidtechnicaltest.ui.architecture.NoSideEffect
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class AlbumViewModel(
    @InjectedParam private val params: Params,
    private val getAlbumDetails: GetAlbumDetails,
    private val playerManager: PlayerManager,
    initialState: State = State()
) : BaseViewModel2<AlbumViewModel.Action, AlbumViewModel.State, NoSideEffect>(initialState) {

    data class Params(val albumId: String)

    override suspend fun onHandle(action: Action) = when (action) {
        is Action.LoadAlbum -> loadAlbum()
        is Action.PlayAlbum -> playAlbum()
    }

    private fun playAlbum() {
        val (_, tracks) = state.value
        playerManager.playTracks(tracks)
    }

    private suspend fun loadAlbum() {
        val albumDetails = getAlbumDetails(GetAlbumDetails.Params(params.albumId))
        updateState { state -> state.copy(album = albumDetails.album, tracks = albumDetails.tracks) }
    }

    sealed class Action : BaseAction {
        data object LoadAlbum : Action()
        data object PlayAlbum : Action()
    }

    data class State(
        val album: Album = Album.EMPTY,
        val tracks: List<Track> = emptyList()
    ) : BaseState

}
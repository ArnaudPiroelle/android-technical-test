package com.majelan.androidtechnicaltest.ui.scenes.album

import com.google.common.truth.Truth.assertThat
import com.majelan.androidtechnicaltest.MainCoroutineRule
import com.majelan.androidtechnicaltest.domain.interactor.GetAlbumDetails
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.AlbumWithTracks
import com.majelan.androidtechnicaltest.domain.model.Artist
import com.majelan.androidtechnicaltest.domain.model.Track
import com.majelan.androidtechnicaltest.observe
import com.majelan.androidtechnicaltest.player.PlayerManager
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getAlbumDetails = mockk<GetAlbumDetails>()
    private val playerManager = mockk<PlayerManager>()
    private val params = AlbumViewModel.Params("albumId")

    @Before
    fun setUp() {
        clearMocks(
            getAlbumDetails,
            playerManager
        )
    }

    @Test
    fun `should update state with album details on action LoadAlbum`() = runTest {
        // Given
        val artist = Artist("id", "artist name")
        val album = Album(
            id = "id",
            title = "album title",
            artist = artist,
            genre = "genre",
            thumbnail = "image",
            totalTrackCount = 1,
            site = "site"
        )
        val track = Track(
            id = "id",
            title = "title",
            artist = artist,
            thumbnail = "image",
            trackNumber = 1,
            duration = 10,
            source = "source"
        )
        val albumWithTracks = AlbumWithTracks(album = album, tracks = listOf(track))
        coEvery { getAlbumDetails(any()) } returns albumWithTracks

        val viewModel = AlbumViewModel(params, getAlbumDetails, playerManager, AlbumViewModel.State())

        // When
        val resultState = viewModel.state.observe(this)
        viewModel.handle(AlbumViewModel.Action.LoadAlbum)

        // Then
        advanceUntilIdle()

        val initialState = AlbumViewModel.State()
        assertThat(resultState.values)
            .containsExactly(
                initialState,
                initialState.copy(album = album, tracks = listOf(track))
            )
            .inOrder()
        resultState.finish()
    }

    @Test
    fun `should play album with tracks on action PlayAlbum`() = runTest {
        // Given
        val artist = Artist("id", "artist name")
        val track = Track(
            id = "id",
            title = "title",
            artist = artist,
            thumbnail = "image",
            trackNumber = 1,
            duration = 10,
            source = "source"
        )
        val tracksSlot = slot<List<Track>>()
        coEvery { playerManager.playTracks(capture(tracksSlot)) } returns Unit

        val viewModel = AlbumViewModel(params, getAlbumDetails, playerManager, AlbumViewModel.State(tracks = listOf(track)))

        // When
        viewModel.handle(AlbumViewModel.Action.PlayAlbum)

        // Then
        advanceUntilIdle()
        coVerify { playerManager.playTracks(any()) }
        assertThat(tracksSlot.captured).isEqualTo(listOf(track))
    }
}
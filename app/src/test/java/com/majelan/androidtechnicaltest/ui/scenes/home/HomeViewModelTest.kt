package com.majelan.androidtechnicaltest.ui.scenes.home

import com.google.common.truth.Truth.assertThat
import com.majelan.androidtechnicaltest.MainCoroutineRule
import com.majelan.androidtechnicaltest.domain.interactor.GetAlbumDetails
import com.majelan.androidtechnicaltest.domain.interactor.GetAlbums
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

class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val getAlbums = mockk<GetAlbums>()
    private val getAlbumDetails = mockk<GetAlbumDetails>()
    private val playerManager = mockk<PlayerManager>()

    private val viewModel = HomeViewModel(getAlbums, getAlbumDetails, playerManager)

    @Before
    fun setUp() {
        clearMocks(
            getAlbums,
            getAlbumDetails,
            playerManager
        )
    }

    @Test
    fun `should update state with album list on action LoadCatalog`() = runTest {
        // Given
        val artist = Artist("id", "artist name")
        val album1 = Album(
            id = "id1",
            title = "album title",
            artist = artist,
            genre = "genre",
            thumbnail = "image",
            totalTrackCount = 1,
            site = "site"
        )

        val album2 = Album(
            id = "id2",
            title = "album title",
            artist = artist,
            genre = "genre",
            thumbnail = "image",
            totalTrackCount = 1,
            site = "site"
        )

        coEvery { getAlbums() } returns listOf(album1, album2)

        // When
        val resultState = viewModel.state.observe(this)
        viewModel.handle(HomeViewModel.Action.LoadCatalog)

        // Then
        advanceUntilIdle()

        val initialState = HomeViewModel.State()
        assertThat(resultState.values)
            .containsExactly(
                initialState,
                initialState.copy(albums = listOf(album1, album2))
            )
            .inOrder()

        resultState.finish()
    }

    @Test
    fun `should play album on action PlayAlbum`() = runTest {
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

        val tracksSlot = slot<List<Track>>()
        coEvery { playerManager.playTracks(capture(tracksSlot)) } returns Unit

        // When
        viewModel.handle(HomeViewModel.Action.PlayAlbum("albumId"))

        // Then
        advanceUntilIdle()
        coVerify { getAlbumDetails(GetAlbumDetails.Params("albumId")) }
        coVerify { playerManager.playTracks(any()) }
        assertThat(tracksSlot.captured).isEqualTo(listOf(track))
    }
}
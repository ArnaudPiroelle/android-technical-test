package com.majelan.androidtechnicaltest.data.repository

import com.google.common.truth.Truth.assertThat
import com.majelan.androidtechnicaltest.data.network.ApiClient
import com.majelan.androidtechnicaltest.data.network.response.CatalogResponse
import com.majelan.androidtechnicaltest.data.network.response.MusicResponse
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.Artist
import com.majelan.androidtechnicaltest.domain.model.Track
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkCatalogRepositoryTest {

    private val apiClient = mockk<ApiClient>()
    private val networkCatalogRepository = NetworkCatalogRepository(apiClient)

    @Before
    fun setUp() {
        clearMocks(apiClient)
    }

    @Test
    fun `should return a correctly parsed album list`() = runTest {
        // Given
        val music1 = MusicResponse(
            id = "wake_up_01",
            title = "Intro - The Way Of Waking Up (feat. Alan Watts)",
            album = "Wake Up",
            artist = "The Kyoto Connection",
            genre = "Electronic",
            source = "source",
            image = "image",
            trackNumber = 1,
            totalTrackCount = 12,
            duration = 100,
            site = "website"
        )
        val music2 = MusicResponse(
            id = "wake_up_02",
            title = "Geisha",
            album = "Wake Up",
            artist = "The Kyoto Connection",
            genre = "Electronic",
            source = "source",
            image = "image",
            trackNumber = 2,
            totalTrackCount = 12,
            duration = 100,
            site = "website"
        )
        val music3 = MusicResponse(
            id = "spatial_01",
            title = "Pre-game marching band",
            album = "Spatial Audio",
            artist = "Watson Wu",
            genre = "People",
            source = "source",
            image = "image",
            trackNumber = 2,
            totalTrackCount = 5,
            duration = 100,
            site = "website"
        )

        val catalogResponse = CatalogResponse(music = listOf(music1, music2, music3))

        coEvery { apiClient.getCatalog() } returns catalogResponse

        // When
        val albums = networkCatalogRepository.getAlbums()

        // Then
        val album1 = Album(
            id = "Wake Up",
            title = "Wake Up",
            artist = Artist("The Kyoto Connection", "The Kyoto Connection"),
            genre = "Electronic",
            thumbnail = "image",
            totalTrackCount = 12,
            site = "website"
        )
        val album2 = Album(
            id = "Spatial Audio",
            title = "Spatial Audio",
            artist = Artist("Watson Wu", "Watson Wu"),
            genre = "People",
            thumbnail = "image",
            totalTrackCount = 5,
            site = "website"
        )
        assertThat(albums)
            .containsExactly(album1, album2)
            .inOrder()

    }

    @Test
    fun `should return album with tracks from album id`() = runTest {
        // Given
        val music1 = MusicResponse(
            id = "wake_up_01",
            title = "Intro - The Way Of Waking Up (feat. Alan Watts)",
            album = "Wake Up",
            artist = "The Kyoto Connection",
            genre = "Electronic",
            source = "source",
            image = "image",
            trackNumber = 1,
            totalTrackCount = 12,
            duration = 100,
            site = "website"
        )
        val music2 = MusicResponse(
            id = "wake_up_02",
            title = "Geisha",
            album = "Wake Up",
            artist = "The Kyoto Connection",
            genre = "Electronic",
            source = "source",
            image = "image",
            trackNumber = 2,
            totalTrackCount = 12,
            duration = 101,
            site = "website"
        )
        val music3 = MusicResponse(
            id = "spatial_01",
            title = "Pre-game marching band",
            album = "Spatial Audio",
            artist = "Watson Wu",
            genre = "People",
            source = "source",
            image = "image",
            trackNumber = 2,
            totalTrackCount = 5,
            duration = 100,
            site = "website"
        )

        val catalogResponse = CatalogResponse(music = listOf(music1, music2, music3))
        coEvery { apiClient.getCatalog() } returns catalogResponse

        // When
        val albumWithTracks = networkCatalogRepository.getAlbumWithTracks("Wake Up")

        // Then
        val artist = Artist("The Kyoto Connection", "The Kyoto Connection")
        val album1 = Album(
            id = "Wake Up",
            title = "Wake Up",
            artist = Artist("The Kyoto Connection", "The Kyoto Connection"),
            genre = "Electronic",
            thumbnail = "image",
            totalTrackCount = 12,
            site = "website"
        )
        val track1 = Track(
            id = "wake_up_01",
            title = "Intro - The Way Of Waking Up (feat. Alan Watts)",
            artist = artist,
            thumbnail = "image",
            trackNumber = 1,
            duration = 100,
            source = "source"
        )
        val track2 = Track(
            id = "wake_up_02",
            title = "Geisha",
            artist = artist,
            thumbnail = "image",
            trackNumber = 2,
            duration = 101,
            source = "source"
        )

        assertThat(albumWithTracks.album)
            .isEqualTo(album1)

        assertThat(albumWithTracks.tracks)
            .containsExactly(track1, track2)
            .inOrder()
    }
}
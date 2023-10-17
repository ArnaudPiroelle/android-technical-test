package com.majelan.androidtechnicaltest.domain.interactor

import com.google.common.truth.Truth.assertThat
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.AlbumWithTracks
import com.majelan.androidtechnicaltest.domain.model.Artist
import com.majelan.androidtechnicaltest.domain.model.Track
import com.majelan.androidtechnicaltest.domain.repository.CatalogRepository
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAlbumDetailsTest {

    private val catalogRepository = mockk<CatalogRepository>()
    private val getAlbumDetails = GetAlbumDetails(catalogRepository)

    @Before
    fun setUp() {
        clearMocks(catalogRepository)
    }

    @Test
    fun `should return album details`() = runTest {
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

        coEvery { catalogRepository.getAlbumWithTracks("album1") } returns albumWithTracks

        // When
        val result = getAlbumDetails(GetAlbumDetails.Params("album1"))

        // Then
        coVerify { catalogRepository.getAlbumWithTracks("album1") }
        assertThat(result).isEqualTo(albumWithTracks)
    }
}
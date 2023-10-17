package com.majelan.androidtechnicaltest.domain.interactor

import com.google.common.truth.Truth
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.Artist
import com.majelan.androidtechnicaltest.domain.repository.CatalogRepository
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAlbumsTest {
    private val catalogRepository = mockk<CatalogRepository>()
    private val getAlbums = GetAlbums(catalogRepository)

    @Before
    fun setUp() {
        clearMocks(catalogRepository)
    }

    @Test
    fun `should return an albums list`() = runTest {
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

        coEvery { catalogRepository.getAlbums() } returns listOf(album1, album2)

        // When
        val result = getAlbums()

        // Then
        coVerify { catalogRepository.getAlbums() }
        Truth.assertThat(result)
            .containsExactly(album1, album2)
            .inOrder()
    }
}
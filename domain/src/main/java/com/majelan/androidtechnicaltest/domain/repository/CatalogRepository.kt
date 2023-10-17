package com.majelan.androidtechnicaltest.domain.repository

import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.AlbumWithTracks

interface CatalogRepository {
    suspend fun getAlbums(): List<Album>
    suspend fun getAlbumWithTracks(albumId: String): AlbumWithTracks
}
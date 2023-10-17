package com.majelan.androidtechnicaltest.domain.interactor

import com.majelan.androidtechnicaltest.domain.model.AlbumWithTracks
import com.majelan.androidtechnicaltest.domain.repository.CatalogRepository
import org.koin.core.annotation.Factory

@Factory
class GetAlbumDetails(private val catalogRepository: CatalogRepository) : Interactor<GetAlbumDetails.Params, AlbumWithTracks> {
    override suspend fun invoke(params: Params): AlbumWithTracks {
        return catalogRepository.getAlbumWithTracks(params.albumId)
    }

    data class Params(val albumId: String)
}
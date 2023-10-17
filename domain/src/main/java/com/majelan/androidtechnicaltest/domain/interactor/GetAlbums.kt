package com.majelan.androidtechnicaltest.domain.interactor

import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.repository.CatalogRepository
import org.koin.core.annotation.Factory

@Factory
class GetAlbums(
    private val catalogRepository: CatalogRepository
) : NoParamsInteractor<List<Album>> {
    override suspend fun invoke(): List<Album> {
        return catalogRepository.getAlbums()
    }
}
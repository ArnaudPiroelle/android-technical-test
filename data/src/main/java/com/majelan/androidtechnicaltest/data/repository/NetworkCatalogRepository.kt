package com.majelan.androidtechnicaltest.data.repository

import com.majelan.androidtechnicaltest.data.network.ApiClient
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.AlbumWithTracks
import com.majelan.androidtechnicaltest.domain.model.Artist
import com.majelan.androidtechnicaltest.domain.model.Track
import com.majelan.androidtechnicaltest.domain.repository.CatalogRepository
import org.koin.core.annotation.Single

@Single
class NetworkCatalogRepository(
    private val apiClient: ApiClient,
) : CatalogRepository {
    override suspend fun getAlbums(): List<Album> {
        return apiClient.getCatalog()
            .music
            .groupBy { it.album }
            .map { (albumId, musics) ->
                Album(
                    id = albumId,
                    title = musics[0].album,
                    artist = Artist(id = musics[0].artist, name = musics[0].artist),
                    genre = musics[0].genre,
                    thumbnail = musics[0].image,
                    totalTrackCount = musics[0].totalTrackCount,
                    site = musics[0].site
                )
            }

    }

    override suspend fun getAlbumWithTracks(albumId: String): AlbumWithTracks {
        return apiClient.getCatalog().music.filter { it.album == albumId }
            .sortedBy { it.trackNumber }
            .map { music ->
                val artist = Artist(id = music.artist, name = music.artist)

                val album = Album(
                    id = albumId,
                    title = music.album,
                    artist = artist,
                    genre = music.genre,
                    thumbnail = music.image,
                    totalTrackCount = music.totalTrackCount,
                    site = music.site
                )

                val track = Track(
                    id = music.id,
                    title = music.title,
                    thumbnail = music.image,
                    trackNumber = music.trackNumber,
                    duration = music.duration,
                    source = music.source,
                    artist = artist,
                )

                AlbumWithTracks(album, listOf(track))
            }
            .reduce { old, new ->
                AlbumWithTracks(old.album, old.tracks + new.tracks)
            }
    }

}
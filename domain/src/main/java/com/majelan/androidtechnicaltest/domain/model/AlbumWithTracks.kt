package com.majelan.androidtechnicaltest.domain.model

data class AlbumWithTracks(
    val album: Album,
    val tracks: List<Track>
)
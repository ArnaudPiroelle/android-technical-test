package com.majelan.androidtechnicaltest.domain.model

data class Track(
    val id: String,
    val title: String,
    val artist: Artist,
    val thumbnail: String,
    val trackNumber: Int,
    val duration: Int,
    val source: String,
)

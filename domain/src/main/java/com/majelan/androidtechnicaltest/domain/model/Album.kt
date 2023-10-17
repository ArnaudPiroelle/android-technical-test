package com.majelan.androidtechnicaltest.domain.model

data class Album(
    val id: String,
    val title: String,
    val artist: Artist,
    val genre: String,
    val thumbnail: String,
    val totalTrackCount: Int,
    val site: String,
) {
    companion object {
        val EMPTY = Album(
            id = "",
            title = "",
            artist = Artist.EMPTY,
            genre = "",
            thumbnail = "",
            totalTrackCount = 0,
            site = ""
        )
    }
}

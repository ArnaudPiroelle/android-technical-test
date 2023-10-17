package com.majelan.androidtechnicaltest.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("album") val album: String,
    @SerialName("artist") val artist: String,
    @SerialName("genre") val genre: String,
    @SerialName("source") val source: String,
    @SerialName("image") val image: String,
    @SerialName("trackNumber") val trackNumber: Int,
    @SerialName("totalTrackCount") val totalTrackCount: Int,
    @SerialName("duration") val duration: Int,
    @SerialName("site") val site: String,
)
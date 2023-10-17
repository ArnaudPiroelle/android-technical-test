package com.majelan.androidtechnicaltest.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogResponse(
    @SerialName("music") val music: List<MusicResponse>
)
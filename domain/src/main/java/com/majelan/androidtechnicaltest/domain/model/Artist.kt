package com.majelan.androidtechnicaltest.domain.model

data class Artist(
    val id: String,
    val name: String,
) {
    companion object {
        val EMPTY = Artist(
            id = "",
            name = ""
        )
    }
}

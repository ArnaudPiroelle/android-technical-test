package com.majelan.androidtechnicaltest.data.network

import com.google.common.truth.Truth.assertThat
import com.majelan.androidtechnicaltest.data.network.response.MusicResponse
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ApiClientTest {

    val apiClient = ApiClient(MockedEngine)

    @Test
    fun `should parse json network response`() = runTest {
        // Given


        // When
        val catalog = apiClient.getCatalog()

        // Then
        val musicResponse = MusicResponse(
            id = "wake_up_01",
            title = "Intro - The Way Of Waking Up (feat. Alan Watts)",
            album = "Wake Up",
            artist = "The Kyoto Connection",
            genre = "Electronic",
            source = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3",
            image = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/art.jpg",
            trackNumber = 1,
            totalTrackCount = 13,
            duration = 90,
            site = "http://freemusicarchive.org/music/The_Kyoto_Connection/Wake_Up_1957/"
        )
        assertThat(catalog.music)
            .containsExactly(musicResponse)
            .inOrder()
    }
}

private val json = """
{
  "music": [
    {
      "id": "wake_up_01",
      "title": "Intro - The Way Of Waking Up (feat. Alan Watts)",
      "album": "Wake Up",
      "artist": "The Kyoto Connection",
      "genre": "Electronic",
      "source": "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3",
      "image": "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/art.jpg",
      "trackNumber": 1,
      "totalTrackCount": 13,
      "duration": 90,
      "site": "http://freemusicarchive.org/music/The_Kyoto_Connection/Wake_Up_1957/"
    }
  ]
}
""".trimIndent()

private object MockedEngine : HttpClientEngineFactory<MockEngineConfig> {
    override fun create(block: MockEngineConfig.() -> Unit): HttpClientEngine =
        MockEngine { request ->
            respond(
                content = ByteReadChannel(json),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
}
package com.majelan.androidtechnicaltest.data.network

import com.majelan.androidtechnicaltest.data.network.response.CatalogResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import timber.log.Timber

@Single
class ApiClient(engine: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp) {
    private val client = HttpClient(engine) {
        expectSuccess = true

        install(Logging) {
            level = LogLevel.INFO
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
        }

        install(ContentNegotiation) {
            json(Json {
                this.ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getCatalog(): CatalogResponse {
        return client.get(ENDPOINT_CATALOG).body()
    }

    companion object {
        const val ENDPOINT_CATALOG = "https://storage.googleapis.com/uamp/catalog.json"
    }
}
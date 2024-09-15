package com.plcoding.core.data.networking

import com.plcoding.core.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * A factory class responsible for creating and configuring an instance of [HttpClient].
 * The client is configured to use the CIO engine for asynchronous I/O operations and includes
 * support for content negotiation with JSON serialization and logging.
 */
class HttpClientFactory {

    /**
     * Builds and returns a configured [HttpClient].
     *
     * The [HttpClient] is configured with:
     * - **CIO engine**: for efficient asynchronous I/O.
     * - **ContentNegotiation**: to handle JSON serialization using Kotlinx.
     * - **Logging**: logs all network requests at the [LogLevel.ALL] level.
     * - **Default headers**: sets the content type to JSON and includes an API key for authentication.
     *
     * @return A fully configured instance of [HttpClient].
     */
    fun build(): HttpClient {
        return HttpClient(CIO) {
            // Install ContentNegotiation plugin to handle JSON serialization
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true // Ignores unknown JSON keys to prevent crashes
                    }
                )
            }

            // Install Logging plugin to log network requests and responses
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message = message) // Logs messages using Timber
                    }
                }
                level = LogLevel.ALL // Logs all request and response details
            }

            // Sets default request headers
            defaultRequest {
                contentType(ContentType.Application.Json) // Specifies content type as JSON
                header("x-api-key", BuildConfig.API_KEY)  // Adds API key for authentication
            }
        }
    }
}

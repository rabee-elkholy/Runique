package com.plcoding.core.data.networking

import com.plcoding.core.data.BuildConfig
import com.plcoding.core.data.dto.AccessTokenDto
import com.plcoding.core.data.dto.request.AccessTokenRequest
import com.plcoding.core.domain.session.SessionStorage
import com.plcoding.core.domain.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
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
class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {

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

            // Install Auth plugin to handle authentication using bearer tokens
            install(Auth) {
                // Configure the bearer authentication mechanism
                bearer {

                    // Load the current tokens (access and refresh) from session storage
                    loadTokens {
                        val authInfo = sessionStorage.get()  // Retrieve stored auth info from session storage
                        authInfo?.let {
                            // Return the BearerTokens object containing the access and refresh tokens
                            BearerTokens(
                                accessToken = it.accessToken,
                                refreshToken = it.refreshToken
                            )
                        }
                    }

                    // Handle token refresh when the access token has expired
                    refreshTokens {
                        val authInfo = sessionStorage.get() ?: return@refreshTokens null  // Retrieve the current auth info from session storage

                        // Send a request to refresh the access token using the current refresh token
                        val response = client.post<AccessTokenRequest, AccessTokenDto>(
                            route = ApiRoutes.ACCESS_TOKEN_ROUTE,  // API route to refresh the token
                            body = AccessTokenRequest(
                                refreshToken = authInfo.refreshToken,  // Use the refresh token from the current auth info
                                userId = authInfo.userId  // Send the user ID along with the refresh token
                            )
                        )

                        // Handle the result of the token refresh request
                        when (response) {
                            // If the request is successful, update the stored auth info with the new access token
                            is Result.Success -> {
                                val newAuthInfo = authInfo.copy(
                                    accessToken = response.data.accessToken,  // Update the access token
                                )
                                sessionStorage.set(newAuthInfo)  // Save the updated auth info to session storage

                                // Return the new BearerTokens with the refreshed access token
                                BearerTokens(
                                    accessToken = newAuthInfo.accessToken,
                                    refreshToken = newAuthInfo.refreshToken
                                )
                            }

                            // If the request fails, return empty BearerTokens
                            is Result.Failure -> BearerTokens(
                                accessToken = EMPTY_TOKEN,
                                refreshToken = EMPTY_TOKEN
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EMPTY_TOKEN = ""
    }
}

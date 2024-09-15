package com.plcoding.auth.data.repository

import com.plcoding.auth.data.model.request.RegisterRequest
import com.plcoding.auth.domain.repository.RegisterRepository
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyDataResult
import io.ktor.client.HttpClient

/**
 * Implementation of the [RegisterRepository] interface that handles user registration by
 * making network requests through the provided [HttpClient].
 *
 * This class sends a registration request with the user's email and password to a remote server.
 *
 * @property httpClient The [HttpClient] used for making network requests.
 */
class RegisterRepositoryImpl(
    private val httpClient: HttpClient
) : RegisterRepository {

    /**
     * Registers a user by sending a POST request to the "/register" endpoint.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return An [EmptyDataResult] which will be successful if the registration is completed,
     * or will return a [DataError.Network] if there's a network-related failure.
     */
    override suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}

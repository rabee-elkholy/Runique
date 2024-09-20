package com.plcoding.auth.data.repository

import com.plcoding.auth.data.model.dto.LoginDto
import com.plcoding.auth.data.model.request.LoginRequest
import com.plcoding.auth.data.model.request.RegisterRequest
import com.plcoding.auth.domain.repository.AuthRepository
import com.plcoding.core.data.networking.ApiRoutes
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.session.AuthInfo
import com.plcoding.core.domain.session.SessionStorage
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyDataResult
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.domain.utils.asEmptyDataResult
import io.ktor.client.HttpClient

/**
 * Implementation of the [AuthRepository] interface that handles user registration by
 * making network requests through the provided [HttpClient].
 *
 * This class sends a registration request with the user's email and password to a remote server.
 *
 * @property httpClient The [HttpClient] used for making network requests.
 */
class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    /**
     * Registers a user by sending a POST request to the "/register" endpoint.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return An [EmptyDataResult] which will be successful if the registration is completed,
     * or will return a [DataError.Network] if there's a network-related failure.
     */
    override suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network> =
        httpClient.post<RegisterRequest, Unit>(
            route = ApiRoutes.REGISTER_ROUTE,
            body = RegisterRequest(
                email = email,
                password = password
            )
        )

    /**
     * Login a user by sending a POST request to the "/login" endpoint.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return An [EmptyDataResult] which will be successful if the registration is completed,
     * or will return a [DataError.Network] if there's a network-related failure.
     */
    override suspend fun login(email: String, password: String): EmptyDataResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginDto>(
            route = ApiRoutes.LOGIN_ROUTE,
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        return when (result) {
            is Result.Success -> {
                sessionStorage.set(
                    AuthInfo(
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken,
                        userId = result.data.userId
                    )
                )
                result.asEmptyDataResult()
            }
            else -> result.asEmptyDataResult() // Handle other cases like Result.Error if needed
        }
    }
}


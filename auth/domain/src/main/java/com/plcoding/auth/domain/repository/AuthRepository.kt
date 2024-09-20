package com.plcoding.auth.domain.repository

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyDataResult

/**
 * A repository interface that defines the contract for user registration.
 * It abstracts the logic required to register a user with their email and password.
 */
interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyDataResult<DataError.Network>
}

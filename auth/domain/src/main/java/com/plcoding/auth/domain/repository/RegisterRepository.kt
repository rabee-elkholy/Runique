package com.plcoding.auth.domain.repository

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyDataResult

/**
 * A repository interface that defines the contract for user registration.
 * It abstracts the logic required to register a user with their email and password.
 */
interface RegisterRepository {

    /**
     * Registers a user using the provided [email] and [password].
     *
     * @param email The user's email address used for registration.
     * @param password The user's password used for registration.
     * @return An [EmptyDataResult] containing a [DataError.Network] if registration fails due to a network issue.
     * @throws Exception if any other issue occurs during the registration process.
     */
    suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network>
}

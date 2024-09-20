package com.plcoding.core.domain.session

/**
 * SessionStorage is an interface that defines methods for storing, retrieving,
 * and clearing authentication information.
 */
interface SessionStorage {

    /**
     * Retrieves the stored authentication information.
     *
     * @return The [AuthInfo] object or null if no information is found.
     */
    suspend fun get(): AuthInfo?

    /**
     * Stores the provided [AuthInfo] in the session storage.
     *
     * @param authInfo The [AuthInfo] object to be stored.
     */
    suspend fun set(authInfo: AuthInfo)

    /**
     * Clears the stored authentication information.
     */
    suspend fun clear()
}

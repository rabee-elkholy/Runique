package com.plcoding.core.domain.utils

/**
 * Represents specific types of errors that can occur in the domain layer.
 */
sealed interface DataError : Error {
    /**
     * Enum class representing different types of network-related errors.
     */
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    /**
     * Enum class representing different types of local errors.
     */
    enum class Local : DataError {
        DISK_FULL
    }
}
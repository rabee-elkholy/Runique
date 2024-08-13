package com.plcoding.core.domain.utils

/**
 * Represents a result of an operation, which can either be successful or a failure.
 */
sealed interface Result<out D, out E : Error> {
    /**
     * Represents a successful result containing the data.
     *
     * @param D The type of the data.
     * @property data The successful result data.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed result containing an error.
     *
     * @param E The type of the error which extends [Error].
     * @property error The error causing the failure.
     */
    data class Failure<out E : Error>(val error: E) : Result<Nothing, E>
}

/**
 * Maps the data of a successful [Result] to a new type using the provided [mapper] function.
 *
 * @param D The type of the original data.
 * @param E The type of the error.
 * @param M The type of the mapped data.
 * @param mapper A function that maps the original data to the new data type.
 * @return A [Result] containing the mapped data if the original [Result] was successful, or the original error if it was a failure.
 */
inline fun <D, E : Error, M> Result<D, E>.map(mapper: (D) -> M): Result<M, E> = when (this) {
    is Result.Success -> Result.Success(mapper(data))
    is Result.Failure -> Result.Failure(error)
}

/**
 * Converts a [Result] to an [EmptyDataResult], which is a [Result] containing no data (Unit) but may contain an error.
 *
 * @param D The type of the original data.
 * @param E The type of the error.
 * @return An [EmptyDataResult] with Unit as data if the original [Result] was successful, or the original error if it was a failure.
 */
fun <D, E : Error> Result<D, E>.asEmptyDataResult(): EmptyDataResult<E> = map {}

/**
 * Type alias for a [Result] that contains no data (Unit) but may contain an error.
 *
 * @param E The type of the error.
 */
typealias EmptyDataResult<E> = Result<Unit, E>
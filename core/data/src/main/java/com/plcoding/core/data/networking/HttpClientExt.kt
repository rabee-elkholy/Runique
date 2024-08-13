package com.plcoding.core.data.networking

import com.plcoding.core.data.BuildConfig
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

/**
 * Performs a safe HTTP GET request using [HttpClient] and handles errors gracefully.
 *
 * @param route The endpoint route (relative or absolute) to send the GET request to.
 * @param queryParameters A map of query parameters to include in the request. Defaults to an empty map.
 * @return A [Result] containing either the response of type [Response] or a [DataError.Network].
 */
suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> = safeCall {
    get {
        url(constructRoute(route))
        queryParameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
}

/**
 * Performs a safe HTTP POST request using [HttpClient] and handles errors gracefully.
 *
 * @param route The endpoint route (relative or absolute) to send the POST request to.
 * @param body The request body of type [Request] to send with the POST request.
 * @return A [Result] containing either the response of type [Response] or a [DataError.Network].
 */
suspend inline fun <reified Request : Any, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
): Result<Response, DataError.Network> = safeCall {
    post {
        url(constructRoute(route))
        setBody(body)
    }
}

/**
 * Performs a safe HTTP DELETE request using [HttpClient] and handles errors gracefully.
 *
 * @param route The endpoint route (relative or absolute) to send the DELETE request to.
 * @param queryParameters A map of query parameters to include in the request. Defaults to an empty map.
 * @return A [Result] containing either the response of type [Response] or a [DataError.Network].
 */
suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> = safeCall {
    delete {
        url(constructRoute(route))
        queryParameters.forEach { (key, value) ->
            parameter(key, value)
        }
    }
}

/**
 * Executes the given HTTP request and safely handles exceptions, converting them into a [Result].
 *
 * @param execute The HTTP request to execute.
 * @return A [Result] containing either the response of type [T] or a [DataError.Network].
 */
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Failure(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Failure(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Failure(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * Converts an [HttpResponse] into a [Result] based on the HTTP status code.
 *
 * @param response The HTTP response to convert.
 * @return A [Result] containing either the parsed body of type [T] or a [DataError.Network].
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Network> = when (response.status.value) {
    in 200..299 -> Result.Success(response.body<T>())
    401 -> Result.Failure(DataError.Network.UNAUTHORIZED)
    408 -> Result.Failure(DataError.Network.REQUEST_TIMEOUT)
    409 -> Result.Failure(DataError.Network.CONFLICT)
    413 -> Result.Failure(DataError.Network.PAYLOAD_TOO_LARGE)
    429 -> Result.Failure(DataError.Network.TOO_MANY_REQUESTS)
    in 500..599 -> Result.Failure(DataError.Network.SERVER_ERROR)
    else -> Result.Failure(DataError.Network.UNKNOWN)
}

/**
 * Constructs the full URL for a given route, appending the base URL if necessary.
 *
 * @param route The endpoint route, which can be relative or absolute.
 * @return The full URL as a string.
 */
fun constructRoute(route: String): String = when {
    route.contains(BuildConfig.BASE_URL) -> route
    route.startsWith("/") -> BuildConfig.BASE_URL + route
    else -> BuildConfig.BASE_URL + "/$route"
}

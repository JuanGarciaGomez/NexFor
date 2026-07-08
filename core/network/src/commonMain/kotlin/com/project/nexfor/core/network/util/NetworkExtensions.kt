package com.project.nexfor.core.network.util

import com.project.nexfor.core.network.model.NetworkError
import com.project.nexfor.core.network.model.NetworkResult
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import kotlinx.serialization.SerializationException

/**
 * Executes a network call safely, catching common Ktor and Serialization exceptions
 * and mapping them to a [NetworkResult.Error] with the appropriate [NetworkError].
 *
 * @param T The type of the data payload.
 * @param call The suspend function performing the network request.
 * @return A [NetworkResult] containing the data or a mapped error.
 */
suspend fun <T> safeApiCall(call: suspend () -> NetworkResult<T>): NetworkResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        val networkError = when (e) {
            is HttpRequestTimeoutException,
            is ConnectTimeoutException,
            is SocketTimeoutException -> NetworkError.TimeoutError

            is ResponseException -> NetworkError.HttpError(
                statusCode = e.response.status.value,
                errorCode = null, // Backend specific error code would usually be in the body, which ResponseException doesn't directly parse as ApiResponseDto
                message = e.message ?: "Server Error"
            )

            is SerializationException -> NetworkError.SerializationError(e)

            // Depending on the platform engine, connection errors might manifest differently.
            // Usually, UnresolvedAddressException is common for No Internet.
            // Since it's platform-specific in some versions, we catch generic connection issues if needed.
            else -> {
                if (e.message?.contains("UnresolvedAddressException", ignoreCase = true) == true ||
                    e.message?.contains("Unable to resolve host", ignoreCase = true) == true
                ) {
                    NetworkError.NoInternetError
                } else {
                    NetworkError.UnknownError(e)
                }
            }
        }

        NetworkResult.Error(
            message = networkError.message ?: "An unexpected error occurred",
            statusCode = (networkError as? NetworkError.HttpError)?.statusCode ?: 0,
            errorCode = (networkError as? NetworkError.HttpError)?.errorCode,
            errors = null
        )
    }
}

package com.project.nexfor.core.network.model

/**
 * A sealed class representing all possible network-related failure categories.
 */
sealed class NetworkError : Throwable() {
    /**
     * Represents a server-side error with a specific HTTP status code.
     *
     * @property statusCode The HTTP status code (e.g., 401, 500).
     * @property errorCode A specific backend error identifier.
     * @property message The error description from the server.
     */
    data class HttpError(
        val statusCode: Int,
        val errorCode: String?,
        override val message: String
    ) : NetworkError()

    /**
     * Represents an error during the serialization or deserialization of the JSON payload.
     *
     * @property cause The underlying exception thrown by the serialization library.
     */
    data class SerializationError(override val cause: Throwable) : NetworkError()

    /**
     * Represents a request timeout error.
     */
    object TimeoutError : NetworkError()

    /**
     * Represents a failure to connect to the internet.
     */
    object NoInternetError : NetworkError()

    /**
     * Represents any other unexpected network failure.
     *
     * @property cause The underlying exception.
     */
    data class UnknownError(override val cause: Throwable) : NetworkError()
}

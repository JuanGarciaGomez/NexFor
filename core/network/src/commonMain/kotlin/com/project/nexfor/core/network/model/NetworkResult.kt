package com.project.nexfor.core.network.model

/**
 * A sealed class representing the result of a network operation.
 * This class is used to encapsulate success or failure across the app
 * without exposing raw DTOs from the network layer.
 *
 * @param T The type of the data payload.
 */
sealed class NetworkResult<out T> {
    /**
     * Represents a successful network operation.
     *
     * @property data The payload returned by the server.
     * @property message The message from the server.
     * @property statusCode The HTTP status code.
     */
    data class Success<out T>(
        val data: T,
        val message: String,
        val statusCode: Int
    ) : NetworkResult<T>()

    /**
     * Represents a failed network operation.
     *
     * @property message The human-readable error description.
     * @property statusCode The HTTP status code.
     * @property errorCode The backend-specific error identifier.
     * @property errors List of detailed error messages (e.g., validation errors).
     */
    data class Error(
        val message: String,
        val statusCode: Int,
        val errorCode: String? = null,
        val errors: List<String>? = null
    ) : NetworkResult<Nothing>()
}

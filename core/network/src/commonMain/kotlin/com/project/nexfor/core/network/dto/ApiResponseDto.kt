package com.project.nexfor.core.network.dto

import kotlinx.serialization.Serializable

/**
 * A generic data class that models the standard backend JSON envelope.
 *
 * @param T The type of the data payload.
 * @property success Indicates if the operation was successful.
 * @property statusCode The HTTP status code returned by the backend.
 * @property errorCode A unique error identifier (null if successful).
 * @property message A human-readable message describing the result.
 * @property data The actual payload (null on failure or when not applicable).
 * @property errors A list of detailed validation error messages (null if not applicable).
 * @property timestamp The ISO8601 timestamp when the response was generated.
 * @property path The endpoint path that processed the request.
 */
@Serializable
data class ApiResponseDto<T>(
    val success: Boolean,
    val statusCode: Int,
    val errorCode: String? = null,
    val message: String,
    val data: T? = null,
    val errors: List<String>? = null,
    val timestamp: String,
    val path: String
)

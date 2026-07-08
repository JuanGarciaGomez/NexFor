package com.project.nexfor.core.network.util

import com.project.nexfor.core.network.dto.ApiResponseDto
import com.project.nexfor.core.network.model.NetworkResult

/**
 * Maps an [ApiResponseDto] to a [NetworkResult].
 *
 * @param T The type of the data payload.
 * @return A [NetworkResult.Success] if the response indicates success, otherwise [NetworkResult.Error].
 */
fun <T> ApiResponseDto<T>.toNetworkResult(): NetworkResult<T> {
    return if (success) {
        @Suppress("UNCHECKED_CAST")
        NetworkResult.Success(
            data = data as T,
            message = message,
            statusCode = statusCode
        )
    } else {
        NetworkResult.Error(
            message = message,
            statusCode = statusCode,
            errorCode = errorCode,
            errors = errors
        )
    }
}

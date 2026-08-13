package com.project.nexfor.data.login.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseDto(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: LoginDataDto?,
    @SerialName("timestamp")
    val timeStamp: String,
    val path: String
)

@Serializable
data class LoginDataDto(
    val firstName: String,
    val lastName: String,
    val accessToken: String,
    val refreshToken: String
)
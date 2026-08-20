package com.project.nexfor.data.login.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDataDto(
    val firstName: String,
    val lastName: String,
    val accessToken: String,
    val refreshToken: String
)
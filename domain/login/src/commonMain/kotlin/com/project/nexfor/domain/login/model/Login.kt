package com.project.nexfor.domain.login.model

data class Login (
    val firstName: String,
    val lastName: String,
    val accessToken: String,
    val refreshToken: String
)
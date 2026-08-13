package com.project.nexfor.data.login.mapper

import com.project.nexfor.data.login.dto.LoginDataDto
import com.project.nexfor.domain.login.model.Login

fun LoginDataDto.toDomain() = Login(
    firstName = firstName,
    lastName = lastName,
    accessToken = accessToken,
    refreshToken = refreshToken
)
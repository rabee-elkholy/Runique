package com.plcoding.core.domain.session

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)

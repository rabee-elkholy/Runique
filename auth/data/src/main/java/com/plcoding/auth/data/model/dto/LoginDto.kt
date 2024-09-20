package com.plcoding.auth.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: String,
)

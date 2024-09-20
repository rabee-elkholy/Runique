package com.plcoding.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenDto(
    val accessToken: String,
    val expirationTimestamp: Long
)

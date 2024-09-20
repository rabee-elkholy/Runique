package com.plcoding.core.data.mapper

import com.plcoding.core.data.dto.AuthInfoDto
import com.plcoding.core.domain.session.AuthInfo

fun AuthInfo.toAuthInfoDto() = AuthInfoDto(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)

fun AuthInfoDto.toAuthInfo() = AuthInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)
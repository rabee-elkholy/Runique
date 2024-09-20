package com.plcoding.auth.presentation.login

import com.plcoding.core.presentation.ui.utils.UiText

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class LoginError(val message: UiText) : LoginEvent
}
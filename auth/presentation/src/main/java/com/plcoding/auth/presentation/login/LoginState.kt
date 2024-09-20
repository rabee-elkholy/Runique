package com.plcoding.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState (
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isLogging: Boolean = false,
) {
    val canLogin: Boolean get() = email.text.isNotEmpty() && password.text.isNotEmpty() && !isLogging
}
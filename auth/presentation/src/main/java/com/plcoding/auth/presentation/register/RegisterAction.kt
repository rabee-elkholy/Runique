package com.plcoding.auth.presentation.register

sealed interface RegisterAction {
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data object OnTogglePasswordVisibilityClick : RegisterAction
    data object OnRegisterClick : RegisterAction
}
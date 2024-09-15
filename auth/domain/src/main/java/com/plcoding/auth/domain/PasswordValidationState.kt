package com.plcoding.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasUpperCase: Boolean = false,
    val hasLowerCase: Boolean = false,
    val hasDigit: Boolean = false
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasUpperCase && hasLowerCase && hasDigit
}

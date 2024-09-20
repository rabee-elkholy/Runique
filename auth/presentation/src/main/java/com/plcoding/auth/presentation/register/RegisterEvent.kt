package com.plcoding.auth.presentation.register

import com.plcoding.core.presentation.ui.utils.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class RegistrationError(val message: UiText) : RegisterEvent
}
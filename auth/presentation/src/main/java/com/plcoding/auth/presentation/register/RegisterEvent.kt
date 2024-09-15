package com.plcoding.auth.presentation.register

import com.plcoding.core.presentation.ui.utils.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class RegistrationFailure(val message: UiText) : RegisterEvent
}
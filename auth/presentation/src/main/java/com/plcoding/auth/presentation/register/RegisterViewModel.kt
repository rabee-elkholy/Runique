package com.plcoding.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.domain.UserDataValidator
import com.plcoding.auth.domain.repository.AuthRepository
import com.plcoding.auth.presentation.R
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.presentation.ui.utils.UiText
import com.plcoding.core.presentation.ui.utils.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnTogglePasswordVisibilityClick -> changePasswordVisibility()

            RegisterAction.OnRegisterClick -> register()

            is RegisterAction.OnEmailChange -> validateEmail(action.email)
            is RegisterAction.OnPasswordChange -> validatePassword(action.password)
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = authRepository.register(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isRegistering = false)

            handleRegisterResult(result)
        }
    }

    private suspend fun handleRegisterResult(result: Result<Unit, DataError.Network>) {
        when (result) {
            is Result.Failure -> {
                if (result.error == DataError.Network.CONFLICT)
                    eventChannel.send(
                        RegisterEvent.RegistrationError(
                            message = UiText.StringResource(
                                R.string.error_email_exist
                            )
                        )
                    )
                else eventChannel.send(RegisterEvent.RegistrationError(result.error.asUiText()))
            }

            is Result.Success -> {
                eventChannel.send(RegisterEvent.RegistrationSuccess)
            }
        }
    }

    private fun changePasswordVisibility() {
        state = state.copy(
            isPasswordVisible = !state.isPasswordVisible
        )
    }

    private fun validateEmail(email: String) {
        state = state.copy(
            isEmailValid = userDataValidator.isValidEmail(email = email)
        )
    }

    private fun validatePassword(password: String) {
        state = state.copy(
            passwordValidationState = userDataValidator.isValidPassword(password = password)
        )
    }
}
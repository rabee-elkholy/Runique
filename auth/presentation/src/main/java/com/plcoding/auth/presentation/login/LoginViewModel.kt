package com.plcoding.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.domain.repository.AuthRepository
import com.plcoding.auth.presentation.R
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.presentation.ui.utils.UiText
import com.plcoding.core.presentation.ui.utils.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibilityClick -> changePasswordVisibility()
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLogging = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLogging = false)

            handleRegisterResult(result)
        }
    }

    private suspend fun handleRegisterResult(result: Result<Unit, DataError.Network>) {
        when (result) {
            is Result.Failure -> {
                if (result.error == DataError.Network.UNAUTHORIZED)
                    eventChannel.send(
                        LoginEvent.LoginError(
                            message = UiText.StringResource(
                                R.string.error_invalid_credentials
                            )
                        )
                    )
                else eventChannel.send(LoginEvent.LoginError(result.error.asUiText()))
            }

            is Result.Success -> {
                eventChannel.send(LoginEvent.LoginSuccess)
            }
        }
    }

    private fun changePasswordVisibility() {
        state = state.copy(
            isPasswordVisible = !state.isPasswordVisible
        )
    }
}
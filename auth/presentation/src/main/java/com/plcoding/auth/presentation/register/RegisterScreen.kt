package com.plcoding.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.auth.domain.UserDataValidator
import com.plcoding.auth.presentation.R
import com.plcoding.core.presentation.designsystem.CheckIcon
import com.plcoding.core.presentation.designsystem.EmailIcon
import com.plcoding.core.presentation.designsystem.Poppins
import com.plcoding.core.presentation.designsystem.RuniqueGray
import com.plcoding.core.presentation.designsystem.RuniqueTheme
import com.plcoding.core.presentation.designsystem.components.GradientBackground
import com.plcoding.core.presentation.designsystem.components.PasswordRequirement
import com.plcoding.core.presentation.designsystem.components.RuniqueActionBtn
import com.plcoding.core.presentation.designsystem.components.RuniquePasswordTextField
import com.plcoding.core.presentation.designsystem.components.RuniqueTextField
import com.plcoding.core.presentation.ui.utils.ObserveOneTimeEvent
import com.plcoding.core.presentation.ui.utils.ToastUtils
import org.koin.androidx.compose.koinViewModel

@ExperimentalFoundationApi
@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onRegistrationSuccess: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveOneTimeEvent(viewModel.events) { event ->
        when (event) {
            RegisterEvent.RegistrationSuccess -> {
                ToastUtils.showShortToast(
                    context = context,
                    message = context.getString(R.string.message_registration_success),
                    keyboardController = keyboardController
                )
                onRegistrationSuccess()
            }
            is RegisterEvent.RegistrationFailure -> {
                ToastUtils.showLongToast(
                    context = context,
                    message = event.message.asString(context),
                    keyboardController = keyboardController
                )
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSignInClick = onSignInClick
    )
}

@ExperimentalFoundationApi
@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    onSignInClick: () -> Unit,
) {
    LaunchedEffect(state.email.text) {
        val email = state.email.text.trim()
        onAction(RegisterAction.OnEmailChange(email = email.toString()))
    }

    LaunchedEffect(state.password.text) {
        val password = state.password.text
        onAction(RegisterAction.OnPasswordChange(password = password.toString()))
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = RuniqueGray
                    )
                ) {
                    append(text = stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text_sign_in",
                        annotation = stringResource(id = R.string.sign_in)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Poppins,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(stringResource(id = R.string.sign_in))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text_sign_in",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onSignInClick()
                    }
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) {
                    CheckIcon
                } else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasDigit
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_lowercase_char,
                ),
                isValid = state.passwordValidationState.hasLowerCase
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_uppercase_char,
                ),
                isValid = state.passwordValidationState.hasUpperCase
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueActionBtn(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun RegisterScreenPreview() {
    RuniqueTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {},
            onSignInClick = {}
        )
    }
}

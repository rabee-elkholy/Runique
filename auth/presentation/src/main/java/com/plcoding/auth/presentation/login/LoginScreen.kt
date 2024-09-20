package com.plcoding.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.auth.presentation.R
import com.plcoding.core.presentation.designsystem.EmailIcon
import com.plcoding.core.presentation.designsystem.RuniqueTheme
import com.plcoding.core.presentation.designsystem.components.GradientBackground
import com.plcoding.core.presentation.designsystem.components.RuniqueActionBtn
import com.plcoding.core.presentation.designsystem.components.RuniqueClickableText
import com.plcoding.core.presentation.designsystem.components.RuniquePasswordTextField
import com.plcoding.core.presentation.designsystem.components.RuniqueTextField
import com.plcoding.core.presentation.ui.utils.ObserveOneTimeEvent
import com.plcoding.core.presentation.ui.utils.ToastUtils
import org.koin.androidx.compose.koinViewModel

@ExperimentalFoundationApi
@Composable
fun LoginScreenRoot(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveOneTimeEvent(viewModel.events) { event ->
        when (event) {
            LoginEvent.LoginSuccess -> onLoginSuccess()

            is LoginEvent.LoginError -> {
                ToastUtils.showLongToast(
                    context = context,
                    message = event.message.asString(context),
                    keyboardController = keyboardController
                )
            }
        }
    }

    LoginScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSignUpClick = onSignUpClick
    )
}

@ExperimentalFoundationApi
@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    onSignUpClick: () -> Unit,
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.hi_there),
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = stringResource(R.string.login_welcome_message),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                title = stringResource(id = R.string.email),
                hint = stringResource(id = R.string.example_email),
                startIcon = EmailIcon,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                title = stringResource(id = R.string.password),
                hint = stringResource(id = R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginAction.OnTogglePasswordVisibilityClick)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueActionBtn(
                text = stringResource(id = R.string.login),
                isLoading = state.isLogging,
                enabled = state.canLogin,
                onClick = {
                    onAction(LoginAction.OnLoginClick)
                }
            )
            Spacer(modifier = Modifier.weight(weight = 1f))
            RuniqueClickableText(
                normalTextResId = R.string.do_not_have_an_account,
                clickableTextResId = R.string.sign_up,
                onClickableTextClick = onSignUpClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun LoginScreenPreview() {
    RuniqueTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {},
            onSignUpClick = {}
        )
    }
}

package com.plcoding.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.auth.presentation.R
import com.plcoding.core.presentation.designsystem.LogoIcon
import com.plcoding.core.presentation.designsystem.RuniqueTheme
import com.plcoding.core.presentation.designsystem.components.GradientBackground
import com.plcoding.core.presentation.designsystem.components.RuniqueActionBtn
import com.plcoding.core.presentation.designsystem.components.RuniqueOutlinedActionBtn

@Composable
fun IntroScreenRoot(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        }
    )

}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {
    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = 1f),
            contentAlignment = Alignment.Center
        ) {
            RuniqueLogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_to_runique),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.runique_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.size(32.dp))
            RuniqueActionBtn(
                text = stringResource(id = R.string.sign_up),
                onClick = { onAction(IntroAction.OnSignUpClick) }
            )
            Spacer(modifier = Modifier.size(16.dp))
            RuniqueOutlinedActionBtn(
                text = stringResource(id = R.string.sign_in),
                onClick = { onAction(IntroAction.OnSignInClick) }
            )
        }
    }
}

@Composable
private fun RuniqueLogoVertical(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = stringResource(id = R.string.logo),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = stringResource(id = R.string.runique),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    RuniqueTheme {
        IntroScreen {}
    }
}
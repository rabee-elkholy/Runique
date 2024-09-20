package com.plcoding.core.presentation.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.core.presentation.designsystem.RuniqueBlack
import com.plcoding.core.presentation.designsystem.RuniqueGray
import com.plcoding.core.presentation.designsystem.RuniqueTheme

/**
 * A custom action button with an optional loading indicator.
 *
 * @param modifier A [Modifier] for adjusting the layout or behavior of the button.
 * @param text The text to display on the button.
 * @param enabled Whether the button is enabled for interaction. Default is `true`.
 * @param isLoading Whether to show a loading indicator on the button. Default is `false`.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
fun RuniqueActionBtn(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val progressAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f, label = "Progress alpha animation")
    val textAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "Text alpha animation")

    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = RuniqueGray,
            disabledContentColor = RuniqueBlack
        ),
        shape = RoundedCornerShape(size = 100f),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(alpha = progressAlpha),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 1.5.dp
            )
            Text(
                text = text,
                modifier = Modifier.alpha(alpha = textAlpha),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * A custom outlined action button with an optional loading indicator.
 *
 * @param modifier A [Modifier] for adjusting the layout or behavior of the button.
 * @param text The text to display on the button.
 * @param enabled Whether the button is enabled for interaction. Default is `true`.
 * @param isLoading Whether to show a loading indicator on the button. Default is `false`.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
fun RuniqueOutlinedActionBtn(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val progressAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f, label = "Progress alpha animation")
    val textAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "Text alpha animation")

    OutlinedButton(
        modifier = modifier.height(IntrinsicSize.Min),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(size = 100f),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(alpha = progressAlpha),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 1.5.dp
            )
            Text(
                text = text,
                modifier = Modifier.alpha(alpha = textAlpha),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun RuniqueActionBtnPreview() {
    RuniqueTheme {
        RuniqueActionBtn(
            text = "Login",
            isLoading = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun RuniqueOutlinedActionBtnPreview() {
    RuniqueTheme {
        RuniqueOutlinedActionBtn(
            text = "Login",
            isLoading = false,
            onClick = {}
        )
    }
}
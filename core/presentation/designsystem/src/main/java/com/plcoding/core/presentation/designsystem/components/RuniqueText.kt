package com.plcoding.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.plcoding.core.presentation.designsystem.Poppins
import com.plcoding.core.presentation.designsystem.RuniqueGray

@Composable
fun RuniqueClickableText(
    @StringRes normalTextResId: Int,
    @StringRes clickableTextResId: Int,
    isUnderline: Boolean = false,
    onClickableTextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        // Normal text
        withStyle(
            style = SpanStyle(
                fontFamily = Poppins,
                fontSize = 13.sp,
                color = RuniqueGray
            )
        ) {
            append(stringResource(id = normalTextResId) + " ")
        }

        // Clickable text
        withLink(
            LinkAnnotation.Clickable(
                tag = "clickable_text_sign_up",
                linkInteractionListener = { onClickableTextClick() }
            )
        ) {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = if (isUnderline) TextDecoration.Underline else TextDecoration.None
                )
            ) {
                append(stringResource(id = clickableTextResId))
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier
    )
}
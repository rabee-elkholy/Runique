package com.plcoding.core.presentation.designsystem.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.core.presentation.designsystem.RuniqueTheme

/**
 * A composable that creates a background with a radial gradient effect. This gradient background
 * can be customized with a modifier, and it supports optional toolbar padding.
 *
 * @param modifier Modifier to be applied to the outer Box. Defaults to [Modifier].
 * @param hasToolbar A Boolean indicating whether the system bars padding should be applied.
 * If true, the system bars padding is removed, assuming a toolbar is present. Defaults to true.
 * @param content A composable lambda for the content to be displayed within the gradient background.
 *
 * The gradient effect is based on the screen dimensions and creates a radial gradient centered
 * horizontally and slightly off the top of the screen. The blur effect is applied only on devices
 * running Android 12 (API 31) or higher.
 *
 * This composable is useful for creating visually appealing backgrounds for entire screens.
 */
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    hasToolbar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidth = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val smallDimension = minOf(
        configuration.screenWidthDp.dp,
        configuration.screenHeightDp.dp
    )
    val smallDimensionPx = with(density) { smallDimension.roundToPx() }

    val primaryColor = MaterialTheme.colorScheme.primary

    val isAtLeastAndroid12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .then(other = if (isAtLeastAndroid12) Modifier.blur(radius = smallDimension / 3f) else Modifier)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            if (isAtLeastAndroid12) primaryColor else primaryColor.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(
                            x = screenWidth.toFloat() / 2f,
                            y = -100f
                        ),
                        radius = smallDimensionPx.toFloat() / 2f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(other = if (hasToolbar) Modifier else Modifier.systemBarsPadding()),
        ) {
            content()
        }
    }

}

@Preview
@Composable
private fun GradientBackgroundPreview() {
    RuniqueTheme {
        GradientBackground(
            modifier = Modifier.fillMaxSize(),
        ) {

        }
    }
}
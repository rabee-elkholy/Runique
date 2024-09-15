package com.plcoding.core.presentation.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.SoftwareKeyboardController

/**
 * Utility class for showing Toast messages in the app.
 * Provides methods for displaying short and long toasts, with optional
 * keyboard hiding functionality.
 */
object ToastUtils {

    /**
     * Displays a short-duration Toast message and optionally hides the software keyboard.
     *
     * @param context The context in which the Toast should be displayed.
     * @param message The message to be displayed in the Toast.
     * @param keyboardController Optional [SoftwareKeyboardController] to hide the keyboard after showing the Toast.
     */
    fun showShortToast(
        context: Context,
        message: String,
        keyboardController: SoftwareKeyboardController? = null
    ) {
        showToast(context, message, Toast.LENGTH_SHORT, keyboardController)
    }

    /**
     * Displays a long-duration Toast message and optionally hides the software keyboard.
     *
     * @param context The context in which the Toast should be displayed.
     * @param message The message to be displayed in the Toast.
     * @param keyboardController Optional [SoftwareKeyboardController] to hide the keyboard after showing the Toast.
     */
    fun showLongToast(
        context: Context,
        message: String,
        keyboardController: SoftwareKeyboardController? = null
    ) {
        showToast(context, message, Toast.LENGTH_LONG, keyboardController)
    }

    /**
     * Displays a Toast message with the specified duration and optionally hides the keyboard.
     *
     * @param context The context in which the Toast should be displayed.
     * @param message The message to be displayed in the Toast.
     * @param duration The duration for which the Toast should be shown (short or long).
     * @param keyboardController Optional [SoftwareKeyboardController] to hide the keyboard after showing the Toast.
     */
    private fun showToast(
        context: Context,
        message: String,
        duration: Int,
        keyboardController: SoftwareKeyboardController? = null
    ) {
        Toast.makeText(context, message, duration).show()
        keyboardController?.hide()
    }
}

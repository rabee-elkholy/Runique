package com.plcoding.core.presentation.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * A sealed interface that represents text in the UI, which can be either a dynamic string or a string resource.
 */
sealed interface UiText {

    /**
     * Represents a dynamic string. This is a simple data class that holds a string value.
     *
     * @property value The string content.
     */
    data class DynamicString(val value: String) : UiText

    /**
     * Represents a string resource. This class holds a reference to a string resource ID and
     * optional arguments that can be used to format the string.
     *
     * @property resId The resource ID of the string.
     * @property args The arguments to format the string, if any.
     */
    class StringResource(
        @StringRes val resId: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText

    /**
     * Converts the [UiText] instance to a string. If the instance is a [DynamicString],
     * it returns the dynamic string value. If the instance is a [StringResource],
     * it retrieves the string resource with optional formatting using the provided arguments.
     *
     * This method is designed to be used within a @Composable function.
     *
     * @return The string representation of the [UiText].
     */
    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(resId, *args)
    }

    /**
     * Converts the [UiText] instance to a string using the given [Context].
     * If the instance is a [DynamicString], it returns the dynamic string value.
     * If the instance is a [StringResource], it retrieves the string resource
     * from the [Context] with optional formatting using the provided arguments.
     *
     * @param context The [Context] used to access resources.
     * @return The string representation of the [UiText].
     */
    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId, *args)
    }
}

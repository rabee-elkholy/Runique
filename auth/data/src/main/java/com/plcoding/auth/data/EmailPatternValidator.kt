package com.plcoding.auth.data

import android.util.Patterns
import com.plcoding.auth.domain.PatternValidator

/**
 * Object that provides an email pattern validation implementation of the [PatternValidator] interface.
 * This class checks whether a given string matches the standard email format.
 */
object EmailPatternValidator : PatternValidator {

    /**
     * Checks if the provided email address matches the standard email pattern.
     *
     * @param value The string to validate as an email address.
     * @return `true` if the provided string matches the email pattern, otherwise `false`.
     */
    override fun matches(value: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(value).matches()
}
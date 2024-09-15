package com.plcoding.auth.domain

/**
 * Interface for validating if a string matches a specific pattern.
 */
interface PatternValidator {
    /**
     * Validates whether the provided string matches a pattern.
     *
     * @param value The string to validate.
     * @return `true` if the string matches the pattern, otherwise `false`.
     */
    fun matches(value: String): Boolean
}
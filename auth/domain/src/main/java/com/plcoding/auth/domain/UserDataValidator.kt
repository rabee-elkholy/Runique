package com.plcoding.auth.domain

/**
 * Class for validating user data such as email and password.
 *
 * @param patternValidator Validator used to validate the email pattern.
 */
class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    /**
     * Validates the provided email using the `PatternValidator`.
     *
     * @param email The email string to validate.
     * @return `true` if the email is valid, otherwise `false`.
     */
    fun isValidEmail(email: String): Boolean =
        patternValidator.matches(value = email.trim())

    /**
     * Validates the provided password based on length, digit, uppercase, and lowercase characters.
     *
     * @param password The password string to validate.
     * @return `PasswordValidationState` containing the state of each validation rule.
     */
    fun isValidPassword(password: String): PasswordValidationState {
        val hasMinLength = password.trim().length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasDigit = hasDigit,
            hasUpperCase = hasUpperCase,
            hasLowerCase = hasLowerCase
        )
    }

    companion object {
        /**
         * The minimum required length for a valid password.
         */
        const val MIN_PASSWORD_LENGTH = 9
    }
}
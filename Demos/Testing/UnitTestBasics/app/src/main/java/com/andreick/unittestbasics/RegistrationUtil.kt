package com.andreick.unittestbasics

import androidx.core.text.isDigitsOnly

object RegistrationUtil {

    private val existingUsers = listOf("Philipp", "Karl")

    /**
     * The input is not valid if...
     * ...the username/password is empty
     * ...the username is already taken
     * ...the confirmed password is not the same as the real password
     * ...the password is not numeric
     * ...the password contains less than 3 digits
     */
    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmedPassword: String
    ) = username.isNotEmpty()
            && username !in existingUsers
            && password.isNotEmpty()
            && password == confirmedPassword
            && password.matches(Regex("\\d{3,}"))
}
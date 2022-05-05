package com.andreick.unittestbasics

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Andreick",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
        "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Andreick",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philipp",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `confirmed password different from password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Andreick",
            "123",
            "321"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password not numeric returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Andreick",
            "abc",
            "abc"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password contains less than 3 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Andreick",
            "12",
            "12"
        )
        assertThat(result).isFalse()
    }
}
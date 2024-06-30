package com.jeongu.loginapp.validation

private const val PASSWORD_FORMAT_LENGTH = 7

class SignUpChecker {

    fun isValidInput(input: String): Boolean {
        return input.isNotBlank()
    }

    fun isValidUserId(input: String): Boolean {
        // 영어와 숫자만 입력 가능
        return input.isNotBlank() && input.matches(Regex("^[a-zA-Z0-9]*$"))
    }

    fun isValidPassword(input: String): Boolean {
        return input.isNotBlank() && input.length >= PASSWORD_FORMAT_LENGTH
    }

    fun isValidAge(input: String): Boolean {
        return input.isNotBlank() && input.toInt() in 1..120
    }
}
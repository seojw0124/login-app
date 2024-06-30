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
        // SignUpActivity.kt에서 입력 값 없을 때 0으로 초기화해서 isNotBlank() 없어도 될 듯.
        // 대신 1~120 사이의 값만 입력 가능하도록 함 (입력 값 없어서 0이어도 회원가입 안 되게 하기 위함)
        return input.toInt() in 1..120
    }
}
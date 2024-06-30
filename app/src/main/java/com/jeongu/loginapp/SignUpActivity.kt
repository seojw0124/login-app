package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo
import com.jeongu.loginapp.validation.SignUpChecker

//private const val PASSWORD_FORMAT_LENGTH = 7
private const val AGE_FORMAT_LENGTH = 3

class SignUpActivity : AppCompatActivity() {

    private val TAG = "SignUpActivity"

    private val etInputUserName by lazy { findViewById<EditText>(R.id.et_input_sign_up_user_name) }
    private val etInputUserId by lazy { findViewById<EditText>(R.id.et_input_sign_up_user_id) }
    private val etInputPassword by lazy { findViewById<EditText>(R.id.et_input_sign_up_password) }
    private val etInputAge by lazy { findViewById<EditText>(R.id.et_input_sign_up_age) }
    private val etInputFavoriteDrink by lazy { findViewById<EditText>(R.id.et_input_sign_up_favorite_drink) }

    private var userName = ""
    private var userId = ""
    private var password = ""
    private var age = 0
    private var favoriteDrink = ""

    private var isValidUserName = false
    private var isValidUserId = false
    private var isValidPassword = false
    private var isValidAge = false
    private var isValidFavoriteDrink = false

    private val signUpChecker = SignUpChecker()

    private var isExistUserName = false
    private var isExistUserId = false

    private var isSignUpError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setTextInput()
        signUp()
    }

    private fun setTextInput() {
        setMaxLength(etInputAge)
        checkValidSignUpInput(etInputUserName)
        checkValidSignUpInput(etInputUserId)
        checkValidSignUpInput(etInputPassword)
        checkValidSignUpInput(etInputAge)
        checkValidSignUpInput(etInputFavoriteDrink)
    }

    private fun setMaxLength(editText: EditText) {
        editText.filters = arrayOf(InputFilter.LengthFilter(AGE_FORMAT_LENGTH))
    }

    // 입력값이 유효한지 확인하는 함수
    private fun checkValidSignUpInput(editText: EditText) {
        editText.doAfterTextChanged {
            val inputValue = it?.toString() ?: ""
            when (editText) {
                etInputUserName -> {
                    userName = inputValue
                    isValidUserName = signUpChecker.isValidInput(userName)
                    updateEditTextFocusState(editText)
                }
                etInputUserId -> {
                    userId = inputValue
                    isValidUserId = signUpChecker.isValidUserId(userId)
                    updateEditTextFocusState(editText)
                }
                etInputPassword -> {
                    password = inputValue
                    isValidPassword = signUpChecker.isValidPassword(password)
                    updateEditTextFocusState(editText)
                }
                etInputAge -> {
                    age = if (inputValue.isNotBlank()) inputValue.toInt() else 0
                    isValidAge = signUpChecker.isValidAge(age.toString())
                    updateEditTextFocusState(editText)
                }
                etInputFavoriteDrink -> {
                    favoriteDrink = inputValue
                    isValidFavoriteDrink = signUpChecker.isValidInput(favoriteDrink)
                    updateEditTextFocusState(editText)
                }
            }
        }
    }

    private fun updateEditTextFocusState(editText: EditText) {
        if (isSignUpError) {
            editText.setBackgroundResource(R.drawable.selector_text_input_background)
        }
    }

    private fun signUp() {
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            isExistUserName = Storage.isExistUserName(userName)
            isExistUserId = Storage.isExistUser(userId)
            if (isValidUserName && isValidUserId && isValidPassword && isValidAge && isValidFavoriteDrink && !isExistUserName && !isExistUserId) {
                successSignUp()
            } else {
                isSignUpError = true
                val toastType = setErrorFocus()
                showToast(toastType)
                return@setOnClickListener
            }
        }
    }

    private fun successSignUp() {
        val user = UserInfo(userName, userId, password, age, favoriteDrink)
        Storage.saveUser(user)
        val intent = Intent(this, SignInActivity::class.java)
        intent.apply {
            putExtra("userId", user.userId)
            putExtra("password", user.password)
        }
        setResult(RESULT_OK, intent)
        showToast("all_valid")
        finish()
    }

    private fun setErrorFocus(): String {
        when {
            !isValidUserName -> {
                etInputUserName.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "user_name"
            }
            !isValidUserId -> {
                etInputUserId.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "user_id"
            }
            !isValidPassword -> {
                etInputPassword.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "password"
            }
            !isValidAge -> {
                etInputAge.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "age"
            }
            !isValidFavoriteDrink -> {
                etInputFavoriteDrink.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "favorite_drink"
            }
            isExistUserName -> {
                etInputUserName.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "exist_user_name"
            }
            else -> {
                etInputUserId.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    requestFocus()
                }
                return "exist_user_id"
            }
        }
    }

    private fun showToast(type: String) {
        when (type) {
            "user_name" -> Toast.makeText(this, "이름을 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            "user_id" -> Toast.makeText(this, "아이디를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            "password" -> Toast.makeText(this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            "age" -> Toast.makeText(this, "나이를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            "favorite_drink" -> Toast.makeText(this, "최애 음료를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            "exist_user_name" -> Toast.makeText(this, "이미 존재하는 이름입니다", Toast.LENGTH_SHORT).show()
            "exist_user_id" -> Toast.makeText(this, "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show()
            "all_valid" -> Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo

private const val PASSWORD_FORMAT_LENGTH = 7
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
                    isValidUserName = isValidInput(userName)
                }
                etInputUserId -> {
                    userId = inputValue
                    isValidUserId = isValidUserId(userId)
                }
                etInputPassword -> {
                    password = inputValue
                    isValidPassword = isValidPassword(password)
                }
                etInputAge -> {
                    age = inputValue.toInt()
                    isValidAge = isValidInput(age.toString())
                }
                etInputFavoriteDrink -> {
                    favoriteDrink = inputValue
                    isValidFavoriteDrink = isValidInput(favoriteDrink)
                }
            }
        }
    }

    private fun isValidInput(input: String): Boolean {
        return input.isNotBlank()
    }

    private fun isValidUserId(input: String): Boolean {
        // 영어와 숫자만 입력 가능
        return input.isNotBlank() && input.matches(Regex("^[a-zA-Z0-9]*$"))
    }

    private fun isValidPassword(input: String): Boolean {
        return input.isNotBlank() && input.length >= PASSWORD_FORMAT_LENGTH
    }

    private fun signUp() {
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            if (isValidUserName && isValidUserId && isValidPassword && isValidAge && isValidFavoriteDrink) {
                successSignUp()
            } else {
                when {
                    !isValidUserName -> Log.d(TAG, "isValidUserName: $userName")
                    !isValidUserId -> Log.d(TAG, "isValidUserId: $userId")
                    !isValidPassword -> Log.d(TAG, "isValidPassword: $password")
                    !isValidAge -> Log.d(TAG, "isValidAge: $age")
                    !isValidFavoriteDrink -> Log.d(TAG, "isValidFavoriteDrink: $favoriteDrink")
                }
                showToast(false)
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
        showToast(true)
        finish()
    }

    private fun showToast(isSuccessful: Boolean) {
        if (isSuccessful) {
            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
        }
    }
}
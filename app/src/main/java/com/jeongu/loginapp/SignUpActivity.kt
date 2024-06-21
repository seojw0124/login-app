package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo

class SignUpActivity : AppCompatActivity() {

    private val TAG = "SignUpActivity"

    private var userName = ""
    private var userId = ""
    private var password = ""
    private var age = 0
    private var mbti = ""
    private var isValidUserName = false
    private var isValidUserId = false
    private var isValidPassword = false
    private var isValidAge = false
    private var isValidMbti = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setTextInput()
        signUp()
    }

    private fun setTextInput() {
        val etInputSignUpUserName = findViewById<EditText>(R.id.et_input_sign_up_user_name)
        checkValidInput(etInputSignUpUserName)
        val etInputSignUpUserId = findViewById<EditText>(R.id.et_input_sign_up_user_id)
        checkValidInput(etInputSignUpUserId)
        val etInputSignUpPassword = findViewById<EditText>(R.id.et_input_sign_up_password)
        checkValidInput(etInputSignUpPassword)
        val etInputSignUpAge = findViewById<EditText>(R.id.et_input_sign_up_age)
        checkValidInput(etInputSignUpAge)
        val etInputSignUpMbti = findViewById<EditText>(R.id.et_input_sign_up_mbti)
        checkValidInput(etInputSignUpMbti)
    }

    // 입력값이 유효한지 확인하는 함수
    private fun checkValidInput(editText: EditText) {
        editText.doAfterTextChanged {
            val inputValue = it?.toString() ?: ""
            when (editText.id) {
                R.id.et_input_sign_up_user_name -> {
                    userName = inputValue
                    isValidUserName = isValidInput(userName)
                }
                R.id.et_input_sign_up_user_id -> {
                    userId = inputValue
                    isValidUserId = isValidInput(userId)
                }
                R.id.et_input_sign_up_password -> {
                    password = inputValue
                    isValidPassword = isValidInput(password)
                }
                R.id.et_input_sign_up_age -> {
                    age = inputValue.toInt()
                    isValidAge = isValidInput(age.toString())
                }
                R.id.et_input_sign_up_mbti -> {
                    mbti = inputValue
                    isValidMbti = isValidInput(mbti)
                }
            }
        }
    }

    private fun isValidInput(input: String): Boolean {
        return input.isNotBlank()
    }

    private fun signUp() {
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            if (isValidUserName && isValidUserId && isValidPassword && isValidAge && isValidMbti) {
                successSignUp()
            } else {
                showToast(false)
                return@setOnClickListener
            }
        }
    }

    private fun successSignUp() {
        val user = saveUserInfo()
        val intent = Intent(this, SignInActivity::class.java)
        intent.apply {
            putExtra("userId", user.userId)
            putExtra("password", user.password)
        }
        setResult(RESULT_OK, intent)
        Log.d(TAG, "signUp: $intent")
        showToast(true)
        finish()
    }

    private fun saveUserInfo(): UserInfo {
        val userName = findViewById<EditText>(R.id.et_input_sign_up_user_name).text.toString()
        val userId = findViewById<EditText>(R.id.et_input_sign_up_user_id).text.toString()
        val password = findViewById<EditText>(R.id.et_input_sign_up_password).text.toString()
        val age = findViewById<EditText>(R.id.et_input_sign_up_age).text.toString().toInt()
        val mbti = findViewById<EditText>(R.id.et_input_sign_up_mbti).text.toString()

        val user = UserInfo(userName, userId, password, age, mbti)
        Storage.saveUser(user)

        return user
    }

    private fun showToast(isSuccessful: Boolean) {
        if (isSuccessful) {
            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
        }
    }
}
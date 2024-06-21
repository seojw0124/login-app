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
        val etInputUserName = findViewById<EditText>(R.id.et_input_sign_up_user_name)
        val etInputUserId = findViewById<EditText>(R.id.et_input_sign_up_user_id)
        val etInputPassword = findViewById<EditText>(R.id.et_input_sign_up_password)
        val etInputAge = findViewById<EditText>(R.id.et_input_sign_up_age)
        val etInputMbti = findViewById<EditText>(R.id.et_input_sign_up_mbti)

        checkValidInput(etInputUserName)
        checkValidInput(etInputUserId)
        checkValidInput(etInputPassword)
        checkValidInput(etInputAge)
        checkValidInput(etInputMbti)
    }

    // 입력값이 유효한지 확인하는 함수
    private fun checkValidInput(editText: EditText) {
        editText.doAfterTextChanged {
            val inputValue = it?.toString() ?: ""
            when (editText.id) {
                R.id.et_input_sign_up_user_name -> {
                    userName = inputValue
                    isValidUserName = isValidInput(userName)
                    Log.d(TAG, "userName: $userName")
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
        val user = UserInfo(userName, userId, password, age, mbti)
        Storage.saveUser(user)
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

    private fun showToast(isSuccessful: Boolean) {
        if (isSuccessful) {
            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
        }
    }
}
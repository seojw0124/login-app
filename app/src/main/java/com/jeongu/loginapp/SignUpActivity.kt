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
        etInputSignUpUserName.doAfterTextChanged {
            Log.d(TAG, "userName: ${etInputSignUpUserName.text}")
            userName = it?.toString() ?: ""
            isValidUserName = isValidInput(userName)
        }
        val etInputSignUpUserId = findViewById<EditText>(R.id.et_input_sign_up_user_id)
        etInputSignUpUserId.doAfterTextChanged {
            Log.d(TAG, "userId: ${etInputSignUpUserId.text}")
            userId = it?.toString() ?: ""
            isValidUserId = isValidInput(userId)
        }
        val etInputSignUpPassword = findViewById<EditText>(R.id.et_input_sign_up_password)
        etInputSignUpPassword.doAfterTextChanged {
            Log.d(TAG, "password: ${etInputSignUpPassword.text}")
            password = it?.toString() ?: ""
            isValidPassword = isValidInput(password)
        }
        val etInputSignUpAge = findViewById<EditText>(R.id.et_input_sign_up_age)
        etInputSignUpAge.doAfterTextChanged {
            Log.d(TAG, "age: ${etInputSignUpAge.text}")
            age = it?.toString()?.toInt() ?: 0
            isValidAge = isValidInput(age.toString())
        }
        val etInputSignUpMbti = findViewById<EditText>(R.id.et_input_sign_up_mbti)
        etInputSignUpMbti.doAfterTextChanged {
            Log.d(TAG, "mbti: ${etInputSignUpMbti.text}")
            mbti = it?.toString() ?: ""
            isValidMbti = isValidInput(mbti)
        }
    }

    private fun isValidInput(input: String): Boolean {
        return input.isNotBlank()
    }

    private fun signUp() {
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            if (isValidUserName && isValidUserId && isValidPassword && isValidAge && isValidMbti) {
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                val user = saveUserInfo()
                val intent = Intent(this, SignInActivity::class.java)
                intent.putExtra("userId", user.userId)
                intent.putExtra("password", user.password)
                startActivity(intent)
            } else {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
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
}
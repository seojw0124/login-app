package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private val TAG = "SignInActivity"
    private lateinit var signUpResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setTextInput()
        signIn()
        navigateToSignUp()
    }

    private fun setTextInput() {
        signUpResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val userIdData = it.data?.getStringExtra("userId") ?: ""
                val passwordData = it.data?.getStringExtra("password") ?: ""
                val etInputSignInUserId = findViewById<EditText>(R.id.et_input_sign_in_user_id)
                val etInputSignInPassword = findViewById<EditText>(R.id.et_input_sign_in_password)

                etInputSignInUserId.setText(userIdData)
                etInputSignInPassword.setText(passwordData)
            }
        }
    }

    private fun signIn() {
        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            val userId = findViewById<EditText>(R.id.et_input_sign_in_user_id).text.toString()
            val password = findViewById<EditText>(R.id.et_input_sign_in_password).text.toString()
            // 둘 중 하나라도 입력되지 않았을 경우
            if (userId.isNotBlank() && password.isNotBlank()) { // isNotEmpty()는 공백이 있으면 true를 반환하지만 isNotBlank()는 공백이 있으면 false를 반환
                showToast(true)
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            } else {
                showToast(false)
                return@setOnClickListener
            }
        }
    }

    private fun navigateToSignUp() {
        val btnGoToSignUp = findViewById<Button>(R.id.btn_go_to_sign_up)
        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            signUpResult.launch(intent)
        }
    }

    private fun showToast(isSuccessful: Boolean) {
        if (isSuccessful) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity() {

    private val TAG = "SignInActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setLayout()
        signIn()
        goToSignUp()
    }

    private fun goToSignUp() {
        val btnGoToSignUp = findViewById<Button>(R.id.btn_go_to_sign_up)
        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            val userId = findViewById<EditText>(R.id.et_input_sign_in_user_id).text.toString()
            val password = findViewById<EditText>(R.id.et_input_sign_in_password).text.toString()
            // 둘 중 하나라도 입력되지 않았을 경우
            if (userId.isNotBlank() && password.isNotBlank()) { // isNotEmpty()는 공백이 있으면 true를 반환하지만 isNotBlank()는 공백이 있으면 false를 반환
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "userId: $userId, password: $password")
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(it, "아이디/비밀번호를 확인해주세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    private fun setLayout() {
        val userIdData = intent.getStringExtra("userId")
        val passwordData = intent.getStringExtra("password")

        val etInputSignInUserId = findViewById<EditText>(R.id.et_input_sign_in_user_id)
        etInputSignInUserId.setText(userIdData)
        val etInputSignInPassword = findViewById<EditText>(R.id.et_input_sign_in_password)
        etInputSignInPassword.setText(passwordData)
    }
}
package com.jeongu.loginapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            val userId = findViewById<EditText>(R.id.et_input_user_id).text.toString()
            val password = findViewById<EditText>(R.id.et_input_password).text.toString()
            // 둘 중 하나라도 입력되지 않았을 경우
            if (userId.isNotBlank() && password.isNotBlank()) { // isNotEmpty()는 공백이 있으면 true를 반환하지만 isNotBlank()는 공백이 있으면 false를 반환
                Toast.makeText(this, "성공적으로 로그인하였습니다.", Toast.LENGTH_SHORT).show()
                Log.d("SignInActivity", "userId: $userId, password: $password")
            } else {
                Snackbar.make(it, "아이디와 비밀번호를 입력해주세요.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}
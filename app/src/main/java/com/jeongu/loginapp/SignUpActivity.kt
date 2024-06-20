package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo

class SignUpActivity : AppCompatActivity() {

    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUpComplete = findViewById<Button>(R.id.btn_sign_up_complete)

        btnSignUpComplete.setOnClickListener {
            val userName = findViewById<EditText>(R.id.et_input_sign_up_user_name).text.toString()
            val userId = findViewById<EditText>(R.id.et_input_sign_up_user_id).text.toString()
            val password = findViewById<EditText>(R.id.et_input_sign_up_password).text.toString()
            val age = findViewById<EditText>(R.id.et_input_sign_up_age).text.toString().toInt()
            val mbti = findViewById<EditText>(R.id.et_input_sign_up_mbti).text.toString()

            val user = UserInfo(userName, userId, password, age, mbti)
            Storage.saveUser(user)
            Log.d(TAG, "user: $user")

            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("password", password)

            startActivity(intent)
        }
    }
}
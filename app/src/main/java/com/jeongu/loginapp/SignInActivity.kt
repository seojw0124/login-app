package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo

class SignInActivity : AppCompatActivity() {

    private val TAG = "SignInActivity"
    private lateinit var signUpResult: ActivityResultLauncher<Intent>

    private val etInputSignInUserId by lazy { findViewById<EditText>(R.id.et_input_sign_in_user_id) }
    private val etInputSignInPassword by lazy { findViewById<EditText>(R.id.et_input_sign_in_password) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initUserData()
        setTextInput()
        signIn()
        navigateToSignUp()
    }

    private fun setTextInput() {
        signUpResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val userIdData = it.data?.getStringExtra("userId") ?: ""
                val passwordData = it.data?.getStringExtra("password") ?: ""

                etInputSignInUserId.setText(userIdData)
                etInputSignInPassword.setText(passwordData)
            }
        }
    }

    private fun signIn() {
        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            checkValidSignInInput()
        }
    }

    private fun checkValidSignInInput() {
        val userId = etInputSignInUserId.text.toString()
        val password = etInputSignInPassword.text.toString()
        // 둘 중 하나라도 입력되지 않았을 경우
        if (userId.isNotBlank() && password.isNotBlank()) { // isNotEmpty()는 공백이 있으면 true를 반환하지만 isNotBlank()는 공백이 있어도 false를 반환
            val user = Storage.getUser(userId)
            if (user != null) {
                if (user.password == password) {
                    showToast("success")
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                } else {
                    etInputSignInPassword.apply {
                        setBackgroundResource(R.drawable.selector_text_input_background_red)
                        requestFocus()
                    }
                    showToast("no_password")
                    return
                }
            } else {
                etInputSignInUserId.apply {
                    setBackgroundResource(R.drawable.selector_text_input_background_red)
                    setSelection(text.length)
                    requestFocus()
                }
                etInputSignInPassword.setText("") // vs getText().clear() -> text.clear()는 API 28부터 사용 가능
                showToast("no_user_id")
                return
            }
        } else {
            etInputSignInUserId.apply {
                requestFocus()
                setBackgroundResource(R.drawable.selector_text_input_background_red)
            }
            showToast("blank")
            return
        }
    }

    private fun navigateToSignUp() {
        val btnGoToSignUp = findViewById<Button>(R.id.btn_go_to_sign_up)
        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            signUpResult.launch(intent)
        }
    }

    private fun showToast(type: String) {
        when (type) {
            "success" -> Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            "no_user_id" -> Toast.makeText(this, "아이디가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
            "no_password" -> Toast.makeText(this, "비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show()
            "blank" -> Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUserData() {
        Storage.saveUser(UserInfo("aaaaa", "aaa123", "1234567", 28, "아메리카노"))
        Storage.saveUser(UserInfo("bbbbb", "bbb123", "12345678", 29, "라떼"))
    }
}
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
        setLayout()
        signIn()
        navigateToSignUp()
    }

    private fun setLayout() {
        signUpResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val userIdData = it.data?.getStringExtra("userId") ?: ""
                val passwordData = it.data?.getStringExtra("password") ?: ""

                etInputSignInUserId.apply {
                    setText(userIdData)
                    setSelection(text.length)
                    requestFocus()
                }
                etInputSignInPassword.setText(passwordData)
            }
        }
    }

    private fun signIn() {
        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            checkValidInput()
        }
    }

    private fun checkValidInput() {
        val userId = etInputSignInUserId.text.toString()
        val password = etInputSignInPassword.text.toString()
        // 둘 중 하나라도 입력되지 않았을 경우
        if (userId.isNotBlank() && password.isNotBlank()) { // isNotEmpty()는 공백이 있으면 true를 반환하지만 isNotBlank()는 공백이 있어도 false를 반환
            val user = Storage.getUser(userId)
            if (user != null) {
                if (user.password == password) {
                    succeedSignIn(user)
                } else {
                    failToSignInByPassword()
                }
            } else {
                failToSignInById()
            }
        } else {
            failToSignInByBlankInput()
        }
    }

    private fun succeedSignIn(user: UserInfo?) {
        showToast("success")
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun failToSignInById() {
        etInputSignInUserId.apply {
            setBackgroundResource(R.drawable.selector_text_input_background_red)
            setSelection(text.length)
            requestFocus()
        }
        etInputSignInPassword.setText("")
        showToast("no_user_id")
    }

    private fun failToSignInByPassword() {
        etInputSignInPassword.apply {
            setBackgroundResource(R.drawable.selector_text_input_background_red)
            requestFocus()
        }
        showToast("no_password")
    }

    private fun failToSignInByBlankInput() {
        etInputSignInUserId.apply {
            setBackgroundResource(R.drawable.selector_text_input_background_red)
            requestFocus()
        }
        showToast("blank")
    }

    private fun navigateToSignUp() {
        val btnNavigateToSignUp = findViewById<Button>(R.id.btn_navigate_to_sign_up)
        btnNavigateToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            signUpResult.launch(intent)
        }
    }

    private fun showToast(type: String) {
        val message = when (type) {
            "success" -> "로그인 성공"
            "no_user_id" -> "아이디가 존재하지 않습니다"
            "no_password" -> "비밀번호가 올바르지 않습니다"
            "blank" -> "아이디와 비밀번호를 모두 입력해주세요"
            else -> "unknown"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initUserData() {
        Storage.saveUser(UserInfo("aaaaa", "aaa123", "1234567", 28, "아메리카노"))
        Storage.saveUser(UserInfo("bbbbb", "bbb123", "12345678", 29, "라떼"))
    }
}
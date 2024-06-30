package com.jeongu.loginapp

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo
import com.jeongu.loginapp.validation.SignUpChecker

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

    private val signUpChecker = SignUpChecker()

    private var isExistUserName = false
    private var isExistUserId = false

    private var isSignUpError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setTextInput()
        signUp()
    }

    private fun setTextInput() {
        setMaxLength(etInputAge)
        checkValidInput(etInputUserName)
        checkValidInput(etInputUserId)
        checkValidInput(etInputPassword)
        checkValidInput(etInputAge)
        checkValidInput(etInputFavoriteDrink)
    }

    private fun setMaxLength(editText: EditText) {
        editText.filters = arrayOf(InputFilter.LengthFilter(AGE_FORMAT_LENGTH))
    }

    // 입력값이 유효한지 확인하는 함수
    private fun checkValidInput(editText: EditText) {
        editText.doAfterTextChanged {
            val inputValue = it?.toString() ?: ""
            when (editText) {
                etInputUserName -> {
                    userName = inputValue
                    isValidUserName = signUpChecker.isValidInput(userName)
                }
                etInputUserId -> {
                    userId = inputValue
                    isValidUserId = signUpChecker.isValidUserId(userId)
                }
                etInputPassword -> {
                    password = inputValue
                    isValidPassword = signUpChecker.isValidPassword(password)
                }
                etInputAge -> {
                    // 입력 값이 없을 경우 0으로 초기화 해주지 않으면 입력값 없을 때 위에서 inputValue가 "".toInt()로 에러 발생
                    age = if (inputValue.isNotBlank()) inputValue.toInt() else 0
                    isValidAge = signUpChecker.isValidAge(age.toString())
                }
                etInputFavoriteDrink -> {
                    favoriteDrink = inputValue
                    isValidFavoriteDrink = signUpChecker.isValidInput(favoriteDrink)
                }
            }
            if(isSignUpError) updateEditTextFocusState(editText)
        }
    }

    // 회원가입 실패 시 변경되었던 EditText의 스타일을 원래대로 돌려주는 함수
    private fun updateEditTextFocusState(editText: EditText) {
        editText.setBackgroundResource(R.drawable.selector_text_input_background)
    }

    private fun signUp() {
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            isExistUserName = Storage.isExistUserName(userName)
            isExistUserId = Storage.isExistUser(userId)
            if (isAllInputValid() && !isExistUserName && !isExistUserId) {
                succeedSignUp()
            } else {
                failToSignUp()
                return@setOnClickListener
            }
        }
    }

    private fun failToSignUp() {
        isSignUpError = true
        val toastType = setError()
        showToast(toastType)
    }

    private fun isAllInputValid(): Boolean {
        return isValidUserName && isValidUserId && isValidPassword && isValidAge && isValidFavoriteDrink
    }

    private fun succeedSignUp() {
        val user = UserInfo(userName, userId, password, age, favoriteDrink)
        Storage.saveUser(user)
        val intent = Intent(this, SignInActivity::class.java)
        intent.apply {
            putExtra("userId", user.userId)
            putExtra("password", user.password)
        }
        setResult(RESULT_OK, intent)
        showToast("all_valid")
        finish()
    }

    private fun setError(): String {
        when {
            !isValidUserName -> {
                setEditTextErrorFocus(etInputUserName)
                return "user_name"
            }
            !isValidUserId -> {
                setEditTextErrorFocus(etInputUserId)
                return "user_id"
            }
            !isValidPassword -> {
                setEditTextErrorFocus(etInputPassword)
                return "password"
            }
            !isValidAge -> {
                setEditTextErrorFocus(etInputAge)
                return "age"
            }
            !isValidFavoriteDrink -> {
                setEditTextErrorFocus(etInputFavoriteDrink)
                return "favorite_drink"
            }
            isExistUserName -> {
                setEditTextErrorFocus(etInputUserName)
                return "exist_user_name"
            }
            else -> {
                setEditTextErrorFocus(etInputUserId)
                return "exist_user_id"
            }
        }
    }

    private fun setEditTextErrorFocus(editText: EditText) {
        editText.apply {
            setBackgroundResource(R.drawable.selector_text_input_background_red)
            requestFocus()
        }
    }

    private fun showToast(type: String) {
        val message = when (type) {
            "user_name" -> "이름을 다시 입력해주세요"
            "user_id" -> "아이디를 다시 입력해주세요"
            "password" -> "비밀번호를 다시 입력해주세요"
            "age" -> "나이를 다시 입력해주세요"
            "favorite_drink" -> "최애 음료를 다시 입력해주세요"
            "exist_user_name" -> "이미 존재하는 이름입니다"
            "exist_user_id" -> "이미 존재하는 아이디입니다"
            "all_valid" -> "회원가입 성공"
            else -> "unknown"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
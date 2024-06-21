package com.jeongu.loginapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setLayout()
        navigateToSignIn()
    }

    private fun setLayout() {
        val user = Storage.user
        user?.let { showUserInfo(it) }
    }

    private fun showUserInfo(user: UserInfo) {
        val tvHomeUserId = findViewById<TextView>(R.id.tv_home_user_id)
        tvHomeUserId.text = user?.userId
        val tvHomeUserName = findViewById<TextView>(R.id.tv_home_user_name)
        tvHomeUserName.text = user?.userName
        val tvHomeAge = findViewById<TextView>(R.id.tv_home_age)
        tvHomeAge.text = user?.age.toString()
        val tvHomeMbti = findViewById<TextView>(R.id.tv_home_mbti)
        tvHomeMbti.text = user?.mbti
    }

    private fun navigateToSignIn() {
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }
    }
}
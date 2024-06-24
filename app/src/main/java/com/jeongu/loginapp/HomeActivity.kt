package com.jeongu.loginapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jeongu.loginapp.data.Storage
import com.jeongu.loginapp.data.UserInfo
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    private val drinks = mapOf(
        1 to R.drawable.ic_coffee_1,
        2 to R.drawable.ic_coffee_2,
        3 to R.drawable.ic_coffee_3,
        4 to R.drawable.ic_coffee_4,
        5 to R.drawable.ic_coffee_5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setLayout()
        navigateToSignIn()
    }

    private fun setLayout() {
        setRandomImage()

        val userId = intent.getStringExtra("userId") ?: ""
        val user = Storage.getUser(userId)
        user?.let {
            setUserInfo(it)
        }
    }

    private fun setRandomImage() {
        val random = Random
        val num = random.nextInt(5) + 1
        val ivHomeCoffeeImage = findViewById<ImageView>(R.id.iv_home_image)

        ivHomeCoffeeImage.setImageResource(drinks[num] ?: R.drawable.ic_coffee_1)
        Log.d("HomeActivity", "random: $num")
    }

    private fun setUserInfo(user: UserInfo) {
        val tvHomeUserId = findViewById<TextView>(R.id.tv_home_user_id)
        val tvHomeUserName = findViewById<TextView>(R.id.tv_home_user_name)
        val tvHomeAge = findViewById<TextView>(R.id.tv_home_age)
        val tvHomeFavoriteDrink = findViewById<TextView>(R.id.tv_home_favorite_drink)

        tvHomeUserId.text = String.format(resources.getString(R.string.text_home_user_id), user.userId)
        tvHomeUserName.text = String.format(resources.getString(R.string.text_home_user_name), user.userName)
        tvHomeAge.text = String.format(resources.getString(R.string.text_home_age), user.age.toString())
        tvHomeFavoriteDrink.text = String.format(resources.getString(R.string.text_home_favorite_drink), user.favoriteDrink)
    }

    private fun navigateToSignIn() {
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }
    }
}
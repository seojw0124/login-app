package com.jeongu.loginapp

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.jeongu.loginapp.data.UserInfo
import kotlin.random.Random

private const val RANDOM_IMAGE_COUNT = 5

class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"

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
        signOut()
    }

    private fun setLayout() {
        setRandomImage()

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("user", UserInfo::class.java)
        } else {
            intent.getParcelableExtra("user")
        }
        user?.let {
            setUserInfo(it)
        }
    }

    private fun setRandomImage() {
        val random = Random
        val num = random.nextInt(RANDOM_IMAGE_COUNT) + 1
        val ivHomeCoffeeImage = findViewById<ImageView>(R.id.iv_home_image)

        ivHomeCoffeeImage.setImageResource(drinks[num] ?: R.drawable.ic_coffee_1)
    }

    private fun setUserInfo(user: UserInfo) {
        val tvHomeUserId = findViewById<TextView>(R.id.tv_home_user_id)
        val tvHomeUserName = findViewById<TextView>(R.id.tv_home_user_name)
        val tvHomeAge = findViewById<TextView>(R.id.tv_home_age)
        val tvHomeFavoriteDrink = findViewById<TextView>(R.id.tv_home_favorite_drink)

        tvHomeUserId.text = getString(R.string.format_home_user_id, user.userId)
        tvHomeUserName.text = getString(R.string.format_home_user_name, user.userName)
        tvHomeAge.text = getString(R.string.format_home_age, user.age)
        tvHomeFavoriteDrink.text = getString(R.string.format_home_favorite_drink, user.favoriteDrink)
    }

    private fun signOut() {
        val layoutBtnClose = findViewById<ConstraintLayout>(R.id.layout_btn_close)
        layoutBtnClose.setOnClickListener {
            finish()
        }
    }
}
package com.jeongu.loginapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val userName: String,
    val userId: String,
    val password: String,
    val age: Int,
    val favoriteDrink: String
): Parcelable

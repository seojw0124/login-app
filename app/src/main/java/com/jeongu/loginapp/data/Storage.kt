package com.jeongu.loginapp.data

object Storage {

    private var user: UserInfo? = null

    fun saveUser(user: UserInfo) {
        Storage.user = user
    }

    fun getUser(userId: String): UserInfo? {
        return if (user?.userId == userId) {
            user
        } else {
            null
        }
    }
}
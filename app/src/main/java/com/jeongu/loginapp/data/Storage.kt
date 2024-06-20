package com.jeongu.loginapp.data

object Storage {

    var user: UserInfo? = null
        private set

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
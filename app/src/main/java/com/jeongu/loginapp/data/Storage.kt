package com.jeongu.loginapp.data

object Storage {

    private var userList = mutableListOf<UserInfo>()

    fun saveUser(user: UserInfo) {
        userList.add(user)
    }

    fun getUser(userId: String): UserInfo? {
        return userList.find { it.userId == userId }
    }
}
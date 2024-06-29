package com.jeongu.loginapp.data

object Storage {

    private var userList = mutableListOf<UserInfo>()

    fun saveUser(user: UserInfo) {
        userList.add(user)
    }

    fun getUser(userId: String): UserInfo? {
        return userList.find { it.userId == userId }
    }

    private fun getUserByName(userName: String): UserInfo? {
        return userList.find { it.userName == userName }
    }

    fun isExistUser(userId: String): Boolean {
        return userList.contains(getUser(userId))
    }

    fun isExistUserName(userName: String): Boolean {
        return userList.contains(getUserByName(userName))
    }
}
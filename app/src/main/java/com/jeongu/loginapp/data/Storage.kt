package com.jeongu.loginapp.data

object Storage {

    var userList = mutableListOf<UserInfo>()
        private set

    fun saveUser(user: UserInfo) {
        userList.add(user)
    }

    fun getUser(userId: String): UserInfo? {
        return userList.find { it.userId == userId }
    }

    fun getUserByName(userName: String): UserInfo? {
        return userList.find { it.userName == userName }
    }

    fun isExistUser(userId: String): Boolean {
        return userList.contains(getUser(userId))
    }

    fun isExistUserName(userName: String): Boolean {
        return userList.contains(getUserByName(userName))
    }
}
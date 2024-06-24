package com.jeongu.loginapp.data

object Storage {

//    private var user: UserInfo? = null
    private var userList = mutableListOf<UserInfo>()

//    fun saveUser(user: UserInfo) {
//        Storage.user = user
//    }

    fun saveUser(user: UserInfo) {
        userList.add(user)
    }

//    fun getUser(userId: String): UserInfo? {
//        return if (user?.userId == userId) {
//            user
//        } else {
//            null
//        }
//    }

    fun getUser(userId: String): UserInfo? {
        return userList.find { it.userId == userId }
    }
}
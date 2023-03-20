package com.blissvine.dagerhiltdemo.repository

import com.blissvine.dagerhiltdemo.data.UserDao
import com.blissvine.dagerhiltdemo.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    //insert user details to room
    suspend fun createUserRecords(user: User) : Long {
        return userDao.insertToRoomDatabase(user)
    }

    //get single user details e.g with id 1
    val getUserDetails: Flow<List<User>> get() =  userDao.getUsersDetails()

    //delete single user record
    suspend fun deleteSingleUserRecord() {
        userDao.deleteSingleUserDetails(1)
    }
}
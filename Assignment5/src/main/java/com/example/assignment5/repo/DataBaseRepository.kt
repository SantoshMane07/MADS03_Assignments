package com.example.assignment5.repo

import com.example.assignment5.room_db.daos.UserDao
import com.example.assignment5.room_db.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DatabaseRepository @Inject constructor(
    private val userDao: UserDao,
){
    //Insert User
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    //Get list of users]
    fun getAllUsersFlow(): Flow<List<User>> = userDao.getAllUsersFlow()
    //Get user Id
    fun getUserId():Int = userDao.getUserId()

}
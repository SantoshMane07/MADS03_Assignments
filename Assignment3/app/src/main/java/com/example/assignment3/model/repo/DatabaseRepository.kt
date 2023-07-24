package com.example.assignment3.model.repo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment3.model.datastore.DataStoreManager
import com.example.assignment3.model.room_db.daos.UserDao
import com.example.assignment3.model.room_db.entites.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
    private val userDao:UserDao,
    private val dataStoreManager: DataStoreManager
    ){
    //Functions Related to SQLite
    suspend fun inserSingletUser(user: User) {
        return userDao.inserSingletUser(user)
    }
    suspend fun insertListOfUsers(UsersList:List<User>){
        return userDao.insertListOfUsers(UsersList)
    }


    suspend fun deleteSingleUser(user:User) {
        return userDao.deleteSingleUser(user)
    }

    fun getAllUsersFlow(): Flow<List<User>> = userDao.getAllUsersFlow()

    fun getSingleUser(id:Long): User = userDao.getSingleUser(id)

    fun getAllUsers(): List<User> = userDao.getAllUsers()

    //Related to DataStore
    val authenticated = dataStoreManager.authenticated

    suspend fun saveAuthenticated(authenticated: Boolean) {
        dataStoreManager.saveAuthenticated(authenticated = authenticated)
    }
}
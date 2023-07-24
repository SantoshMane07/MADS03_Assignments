package com.example.assignment3.model.room_db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment3.model.room_db.entites.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserSingletUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfUsers(UsersList:List<User>)

    @Delete
    suspend fun deleteSingleUser(user:User)

    @Query("SELECT * FROM User")
    fun getAllUsersFlow(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE user_id = :id")
    fun getSingleUser(id:Long): User

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
}
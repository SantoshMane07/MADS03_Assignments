package com.example.assignment5.room_db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment5.room_db.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //Insert user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    //Get users list
    @Query("SELECT * FROM User")
    fun getAllUsersFlow(): Flow<List<User>>

    //Get User Id
    @Query("Select user_id From User")
    fun getUserId():Int

}


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSingletUser(user: User)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertListOfUsers(UsersList:List<User>)
//
//    @Delete
//    suspend fun deleteSingleUser(user:User)
//
//    @Query("SELECT * FROM User")
//    fun getAllUsersFlow(): Flow<List<User>>
//
//    @Query("SELECT * FROM User WHERE user_id = :id")
//    fun getSingleUser(id:Long): User
//
//    @Query("SELECT * FROM User")
//    fun getAllUsers(): List<User>

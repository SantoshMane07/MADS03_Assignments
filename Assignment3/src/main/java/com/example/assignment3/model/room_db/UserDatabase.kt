package com.example.assignment3.model.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment3.model.room_db.daos.UserDao
import com.example.assignment3.model.room_db.entites.User

@Database(
    version = 1,
    entities = [User::class]
)
abstract class UserDatabase : RoomDatabase(){

    abstract val userDao: UserDao
}
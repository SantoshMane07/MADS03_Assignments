package com.example.assignment5.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment5.room_db.daos.UserDao
import com.example.assignment5.room_db.entities.User


@Database(
    version = 1,
    entities = [User::class]
)
abstract class UserDatabase : RoomDatabase(){

    abstract val userDao: UserDao
}
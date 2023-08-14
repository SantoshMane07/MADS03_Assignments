package com.example.assignment5.room_db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @ColumnInfo(name = "user_id")
    @PrimaryKey
    val userId:Int,
    @ColumnInfo(name = "user_name")
    val userName:String,
    @ColumnInfo(name = "user_fullName")
    val fullName:String,
    @ColumnInfo(name = "user_email")
    val email:String,
    @ColumnInfo(name = "user_biography")
    val biography:String,
    @ColumnInfo(name = "user_postsCount")
    val postsCount:Int,
    @ColumnInfo(name = "user_followers")
    val followers:Int,
    @ColumnInfo(name = "user_following")
    val following:Int,
    @ColumnInfo(name = "user_profilePicUrl")
    val profilePicUrl:String
)

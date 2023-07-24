package com.example.assignment3.model.room_db.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.jar.Attributes.Name

@Entity
data class User(
    @ColumnInfo(name = "user_id")
    @PrimaryKey
    val userId:Long,
    @ColumnInfo(name = "user_name")
    val userName:String,
    @ColumnInfo(name = "user_fullName")
    val fullName:String,
    @ColumnInfo(name = "user_email")
    val email:String
)
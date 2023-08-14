package com.example.assignment5.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserModel(
    val userId:Int,
    @Json(name = "username")
    val userName:String,
    val fullName:String,
    val email:String,
    val biography:String,
    val postsCount:Int,
    val followers:Int,
    val following:Int,
    val profilePicUrl:String
)
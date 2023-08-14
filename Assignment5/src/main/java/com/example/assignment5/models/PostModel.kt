package com.example.assignment5.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostModel(
    val postId: Int,
    val caption: String,
    val url: String,
    val likesCount: Int,
    val commentsCount: Int,
    val user: UserModel,
    val createdAt:String,
)
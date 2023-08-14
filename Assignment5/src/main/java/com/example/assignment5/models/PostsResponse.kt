package com.example.assignment5.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsResponse(
    val status:Boolean,
    val data: PostsResponseData,
)

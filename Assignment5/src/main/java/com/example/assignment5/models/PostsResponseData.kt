package com.example.assignment5.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsResponseData(
    val posts: List<PostModel>,
    val currentPage: Int,
    val hasNextPage: Boolean
)

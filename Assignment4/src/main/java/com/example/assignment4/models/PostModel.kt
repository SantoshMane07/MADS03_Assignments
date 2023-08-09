package com.example.assignment4.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostModel(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val download_url: String,
    val url: String
)
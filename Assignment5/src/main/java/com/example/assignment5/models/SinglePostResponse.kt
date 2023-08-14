package com.example.assignment5.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SinglePostResponse(
    val status:Boolean,
    val data: DataPost,
)
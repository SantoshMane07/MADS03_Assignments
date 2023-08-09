package com.example.assignment4.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subscription(
    val plan: String,
    val status: String
)
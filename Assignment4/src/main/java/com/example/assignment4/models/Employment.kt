package com.example.assignment4.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employment(
    @Json(name = "title")
    val jobTitle: String
)
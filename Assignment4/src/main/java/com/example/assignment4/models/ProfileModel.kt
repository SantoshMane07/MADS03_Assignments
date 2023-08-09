package com.example.assignment4.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileModel(

    var first_name: String,
    var last_name: String,
    var username: String,
    var email: String,
    @Json(name = "avatar")
    var profilePic: String,
    var employment: Employment,
    var address: Address,
    var subscription: Subscription,

    )
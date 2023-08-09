package com.example.assignment4.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(

    var city: String,
    var street_name: String,
    var street_address: String,
    var zip_code: String,
    var state: String,
    var country: String

)
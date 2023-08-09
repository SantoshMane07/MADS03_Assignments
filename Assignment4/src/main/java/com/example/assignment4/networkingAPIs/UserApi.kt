package com.example.assignment4.networkingAPIs

import com.example.assignment4.models.ProfileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    //https://random-data-api.com/api/v2/users?size=1

    @GET("api/v2/users?size=1")
    suspend fun getUserProfile(): Response<ProfileModel>
}

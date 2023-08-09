package com.example.assignment4.networkingAPIs

import com.example.assignment4.models.PostModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApi {
    //https://picsum.photos/v2/list?page=1&limit=10
    @GET("v2/list")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("limit") size: Int
    ): List<PostModel>

    @GET("id/{postId}/info")
    suspend fun getSinglePost(@Path("postId") postId: Long): Response<PostModel>
}
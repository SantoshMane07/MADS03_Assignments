package com.example.assignment5.networking_apis

import com.example.assignment5.models.DeletePostRequest
import com.example.assignment5.models.PostModel
import com.example.assignment5.models.PostsResponse
import com.example.assignment5.models.PostsResponseData
import com.example.assignment5.models.RandomUserResponse
import com.example.assignment5.models.SinglePostResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersAndPostsApi {

    // Get All posts https://api.hadiyarajesh.com/v1/posts?page=0&size=10
    // Get Random User https://api.hadiyarajesh.com/v1/users/random
    // Get Posts by User https://api.hadiyarajesh.com/v1/posts/users/10010

    // Get all posts
    @GET("v1/posts")
    suspend fun getAllPosts(
        @Query("page") page: Int =0,
        @Query("size") size: Int =10
    ): PostsResponse

    //Get Random User
    @GET("/v1/users/random")
    suspend fun getRandomUser(): Response<RandomUserResponse>

    //https://api.hadiyarajesh.com/v1/posts/users/10236?page=0&size=10
    //Get all Posts by User id
    @GET("/v1/posts/users/{userId}")
    suspend fun getPostsByUserId(
        @Path("userId") userId: Int,
        @Query("page") page:Int =0,
        @Query("size") size: Int =10
    ): PostsResponse

    //Get post by Id
    @GET("/v1/posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): Response<SinglePostResponse>

    //Delete Post By Id
    //@DELETE("/v1/posts/user/{postId}")
    @HTTP(method = "DELETE", path = "/v1/posts/{postId}", hasBody = true)
    suspend fun deletePostbypostId(
        @Path(value = "postId", encoded = false) postId: Int,
        @Body deletePostRequest: DeletePostRequest
    ): Response<PostModel>
}
package com.example.assignment5.repo

import com.example.assignment5.models.DeletePostRequest
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_apis.UsersAndPostsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostDetailRepository @Inject constructor(
    private val usersAndPostsApi: UsersAndPostsApi
) {
    //

    //Delete Single Post by Id
    suspend fun deletePostById(
        postId: Int, delreq: DeletePostRequest ,onSuccess: () -> Unit, onFailure: (String, Int) -> Unit
    ) {
        //Getting User detail from Local DBs
        val response = usersAndPostsApi.deletePostbypostId(postId, delreq)
        if (response.isSuccessful) {
            //debug("in in ${response.body()!!.postId}")
            //response.body()?.let { onSuccess(it) } giving null value in response and not postModel which is deleted
            onSuccess()
        } else {
            val errorMsg = response.errorBody()?.string()
            errorMsg?.let { onFailure(errorMsg,response.code()) }
        }
    }

    //Get Post By Id usersAndPostsApi.getPostById(postId = postId)
    suspend fun getPostById(
        postId: Int, onSuccess: (PostModel) -> Unit, onFailure: (String, Int) -> Unit
    ) {
        val response = usersAndPostsApi.getPostById(postId = postId)
        if (response.isSuccessful) {
            onSuccess(response.body()!!.data.post)
        } else {
            val errorMsg = response.errorBody()?.string()
            if (errorMsg != null) {
                onFailure(errorMsg, response.code())
            }
        }
    }
}
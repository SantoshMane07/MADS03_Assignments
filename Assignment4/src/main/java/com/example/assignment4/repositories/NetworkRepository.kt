package com.example.assignment4.repositories

import androidx.compose.runtime.MutableState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment4.debug
import com.example.assignment4.models.PostModel
import com.example.assignment4.models.ProfileModel
import com.example.assignment4.networkingAPIs.PostsApi
import com.example.assignment4.networkingAPIs.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkingRepository @Inject constructor(
    private val userApi: UserApi,
    private val postsApi: PostsApi,

    ) {

    //Related to User Profile Detail
    suspend fun getUserDetail(
        onSuccess: (ProfileModel) -> Unit,
        onFailure: (String, Int) -> Unit
    ) {
        val response = userApi.getUserProfile()
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            val errorMsg = response.errorBody()?.string()
            if (errorMsg != null) {
                onFailure(errorMsg, response.code())
            }
        }
    }

    //Related to Posts
    fun getPosts(): Flow<PagingData<PostModel>> =
        Pager(config = PagingConfig(pageSize = 10)) {
            PagingSourceRepository(postsApi = postsApi)
        }.flow

    //Related to Single Post
    suspend fun getSinglePost(
        postId: Long,
        onSuccess: (PostModel) -> Unit,
        onFailure: (String, Int) -> Unit
    ) {
        val response = postsApi.getSinglePost(postId)
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            val errorMsg = response.errorBody()?.string()
            if (errorMsg != null) {
                onFailure(errorMsg, response.code())
            }
        }
    }
}
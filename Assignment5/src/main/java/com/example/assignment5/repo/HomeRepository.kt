package com.example.assignment5.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_apis.UsersAndPostsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val usersAndPostsApi: UsersAndPostsApi
) {
    //Get all Posts
    fun getAllPosts(): Flow<PagingData<PostModel>> =
        Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 1, initialLoadSize = 100)) {
            AllPostsPagingSource(usersAndPostsApi)
        }.flow
}
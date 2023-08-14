package com.example.assignment5.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_apis.UsersAndPostsApi
import com.example.assignment5.room_db.daos.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val usersAndPostsApi: UsersAndPostsApi,
    private val userDao: UserDao,
) {
    //
//    val pagingConfig = PagingConfig(
//        pageSize = 20,          // Number of items per page
//        initialLoadSize = 10,   // Number of items for initial load
//        // ... other config options ...
//    )

 //   val myPagingSource = MyPagingSource()
 //   val pagingData = Pager(config = pagingConfig, pagingSourceFactory = { myPagingSource }).flow
    //
    //Get all user Posts
    fun getAllUserPosts(): Flow<PagingData<PostModel>> =
        Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 1, initialLoadSize = 1)) {
            UserAllPostsPagingSource(usersAndPostsApi,userDao.getUserId())
        }.flow
}
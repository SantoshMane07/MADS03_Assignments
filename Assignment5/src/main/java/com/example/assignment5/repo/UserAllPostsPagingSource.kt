package com.example.assignment5.repo

import androidx.compose.runtime.collectAsState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment5.debug
import com.example.assignment5.models.PostModel
import com.example.assignment5.models.PostsResponse
import com.example.assignment5.models.PostsResponseData
import com.example.assignment5.networking_apis.UsersAndPostsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


const val FIRST_PAGE_USER = 0

class UserAllPostsPagingSource(
    private val usersAndPostsApi: UsersAndPostsApi, private val userId: Int
) : PagingSource<Int, PostModel>() {

    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {

        return try {
            val page = params.key ?: FIRST_PAGE_USER

            val postsResponse = usersAndPostsApi.getPostsByUserId(
                userId = userId,
                page = page,
                size = params.loadSize ?: 10
            )
            debug("$page , ${params.loadSize} , $userId ")
            LoadResult.Page(
                data = postsResponse.data.posts,
                //prevKey = if (page == postsResponse.data.currentPage) null else page - 1,
                prevKey = if (page == FIRST_PAGE_USER) null else page - 1,
                nextKey = if (postsResponse.data.posts.isEmpty()) null else page + 1
                //nextKey = if (postsResponse.data.hasNextPage) page + 1 else null
            )
        } catch (e: Exception) {
            debug("$e in UserAllPostsPagingSource")
            LoadResult.Error(e)
        }
    }
}
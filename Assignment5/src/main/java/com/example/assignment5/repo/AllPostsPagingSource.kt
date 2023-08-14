package com.example.assignment5.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment5.debug
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_apis.UsersAndPostsApi


const val FIRST_PAGE = 0

class AllPostsPagingSource(
    private val usersAndPostsApi: UsersAndPostsApi
) : PagingSource<Int, PostModel>() {

    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {

        return try{
            val page = params.key ?: FIRST_PAGE
            val postsResponse = usersAndPostsApi.getAllPosts(
                page = page,
                size = params.loadSize
            )
            LoadResult.Page(
                data = postsResponse.data.posts,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                //nextKey = if (postsResponse.data.posts.isEmpty()) null else page + 1
                nextKey = if (postsResponse.data.hasNextPage) page + 1 else null
            )
        } catch (e: Exception) {
            debug("$e ill")
            LoadResult.Error(e)
        }
    }
}
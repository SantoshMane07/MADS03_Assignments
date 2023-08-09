package com.example.assignment4.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment4.debug
import com.example.assignment4.models.PostModel
import com.example.assignment4.networkingAPIs.PostsApi


const val FIRST_PAGE = 1

class PagingSourceRepository(

    private val postsApi: PostsApi
) : PagingSource<Int, PostModel>() {
    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        return try {
            val page = params.key ?: FIRST_PAGE

            val posts = postsApi.getPosts(
                page = page,
                size = params.loadSize
            )

            LoadResult.Page(
                data = posts,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (posts.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            debug(e.toString())
            LoadResult.Error(e)
        }
    }
}
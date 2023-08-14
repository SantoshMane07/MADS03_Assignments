package com.example.assignment5.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment5.models.PostModel
import com.example.assignment5.models.RandomUserResponse
import com.example.assignment5.models.UserModel
import com.example.assignment5.networking_apis.UsersAndPostsApi
import com.example.assignment5.room_db.entities.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginRepository @Inject constructor(
    private val usersAndPostsApi: UsersAndPostsApi
) {
    //Get Random User
    suspend fun getRandomUser(
        onSuccess: (UserModel) -> Unit,
        onFailure: (String, Int) -> Unit
    ) {
        val response = usersAndPostsApi.getRandomUser()
        if (response.isSuccessful) {
            onSuccess(response.body()!!.data.user)
        } else {
            val errorMsg = response.errorBody()?.string()
            if (errorMsg != null) {
                onFailure(errorMsg, response.code())
            }
        }
    }
}
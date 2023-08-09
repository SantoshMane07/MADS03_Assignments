package com.example.assignment4.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment4.models.PostModel
import com.example.assignment4.models.ProfileModel
import com.example.assignment4.networkingUiStateForProfile.NetworkingUiState
import com.example.assignment4.networkingUiStateForProfile.NetworkingUiStateForSinglePost
import com.example.assignment4.repositories.NetworkingRepository
import com.example.assignment4.repositories.PagingSourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkingViewModel @Inject constructor(
    private val networkingRepository: NetworkingRepository
) : ViewModel() {

    //Related to User Profile Detail
    private val _networkingUiState: MutableStateFlow<NetworkingUiState> =
        MutableStateFlow<NetworkingUiState>(NetworkingUiState.Initial)

    val networkingUiState: StateFlow<NetworkingUiState> get() = _networkingUiState

    //Observing user name to show on top app bar on Successful Response fetching
    private var _username = MutableStateFlow<String>("")
    val username: StateFlow<String> get() = _username.asStateFlow()
    //

    fun getUserDetail() {
        viewModelScope.launch {

            _networkingUiState.value = NetworkingUiState.Loading
            networkingRepository.getUserDetail(onSuccess = { ProfileModel ->
                _networkingUiState.value = NetworkingUiState.Success(ProfileModel)
                _username.value = ProfileModel.username
            }, onFailure = { errorMsg, statusCode ->
                _networkingUiState.value = NetworkingUiState.Error(errorMsg, statusCode)
            })
        }
    }

    //Related to Posts
    val posts: Flow<PagingData<PostModel>> =
        networkingRepository.getPosts().cachedIn(viewModelScope)


    //Related to Single Post
    private val _networkingUiStateForSinglePost: MutableStateFlow<NetworkingUiStateForSinglePost> =
        MutableStateFlow<NetworkingUiStateForSinglePost>(NetworkingUiStateForSinglePost.Initial)

    val networkingUiStateForSinglePost: StateFlow<NetworkingUiStateForSinglePost> get() = _networkingUiStateForSinglePost

    fun getSinglePost(postId: Long) = viewModelScope.launch {
        _networkingUiStateForSinglePost.value = NetworkingUiStateForSinglePost.Loading

        networkingRepository.getSinglePost(postId, onSuccess = { post ->
            _networkingUiStateForSinglePost.value = NetworkingUiStateForSinglePost.Success(post)
        }, onFailure = { errorMsg, statusCode ->
            _networkingUiStateForSinglePost.value =
                NetworkingUiStateForSinglePost.Error(errorMsg, statusCode)
        })
    }
}
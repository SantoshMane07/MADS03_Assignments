package com.example.assignment5.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment5.models.DeletePostRequest
import com.example.assignment5.networking_ui_states.PostDetailScreenUiState
import com.example.assignment5.repo.PostDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postDetailRepository: PostDetailRepository,
) : ViewModel() {

    //Get Post By Id
    private val _postDetailScreenUiState: MutableStateFlow<PostDetailScreenUiState> =
        MutableStateFlow<PostDetailScreenUiState>(PostDetailScreenUiState.Initial)
    val postDetailScreenUiState: StateFlow<PostDetailScreenUiState> get() = _postDetailScreenUiState

    fun getPostById(postId: Int) {
        viewModelScope.launch {

            _postDetailScreenUiState.value = PostDetailScreenUiState.Loading
            postDetailRepository.getPostById(postId = postId, onSuccess = { post ->
                _postDetailScreenUiState.value = PostDetailScreenUiState.Success(post)
            }, onFailure = { errorMsg, statusCode ->
                _postDetailScreenUiState.value = PostDetailScreenUiState.Error(errorMsg, statusCode)
            })
        }
    }


    //Delete Post by post Id
    fun deletePostById(postId: Int, delreq: DeletePostRequest) {
        viewModelScope.launch {
            //
            postDetailRepository.deletePostById(postId = postId,
                delreq = delreq,
                onSuccess = {
                    _postDetailScreenUiState.value = PostDetailScreenUiState.AfterDelete
                },
                onFailure = { errorMsg, statusCode ->
                    _postDetailScreenUiState.value =
                        PostDetailScreenUiState.Error(errorMsg, statusCode)
                })
            //

        }
    }
}
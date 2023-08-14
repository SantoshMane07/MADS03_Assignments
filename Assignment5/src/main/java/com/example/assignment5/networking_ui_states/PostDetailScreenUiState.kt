package com.example.assignment5.networking_ui_states

import com.example.assignment5.models.PostModel

sealed interface PostDetailScreenUiState {

    object Initial : PostDetailScreenUiState

    object Loading : PostDetailScreenUiState

    object AfterDelete : PostDetailScreenUiState

    data class Success(val post: PostModel) : PostDetailScreenUiState

    data class Error(val msg: String, val r_code: Int) : PostDetailScreenUiState
}
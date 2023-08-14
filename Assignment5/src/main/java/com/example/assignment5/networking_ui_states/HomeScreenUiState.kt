package com.example.assignment5.networking_ui_states

import com.example.assignment5.models.PostModel

sealed interface HomeScreenUiState {

    object Initial : HomeScreenUiState

    object Loading : HomeScreenUiState

    data class Success(val posts: ArrayList<PostModel>) : HomeScreenUiState

    data class Error(val msg: String, val r_code: Int) : HomeScreenUiState
}
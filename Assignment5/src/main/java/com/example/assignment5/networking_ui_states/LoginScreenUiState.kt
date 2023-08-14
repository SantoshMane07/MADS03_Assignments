package com.example.assignment5.networking_ui_states

import com.example.assignment5.models.UserModel

interface LoginScreenUiState {
    object Initial : LoginScreenUiState

    object Loading : LoginScreenUiState

    data class Success(val userModel: UserModel) : LoginScreenUiState

    data class Error(val msg: String, val r_code: Int) : LoginScreenUiState
}
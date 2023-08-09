package com.example.assignment4.networkingUiStateForProfile

import com.example.assignment4.models.ProfileModel

sealed interface NetworkingUiState {

    object Initial : NetworkingUiState

    object Loading : NetworkingUiState

    data class Success(val profileDetail: ProfileModel) : NetworkingUiState

    data class Error(val msg: String, val r_code: Int) : NetworkingUiState

}
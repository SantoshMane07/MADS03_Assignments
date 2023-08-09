package com.example.assignment4.networkingUiStateForProfile

import com.example.assignment4.models.PostModel

interface NetworkingUiStateForSinglePost {
    object Initial : NetworkingUiStateForSinglePost

    object Loading : NetworkingUiStateForSinglePost

    data class Success(val postDetails: PostModel) : NetworkingUiStateForSinglePost

    data class Error(val msg: String, val r_code: Int) : NetworkingUiStateForSinglePost
}
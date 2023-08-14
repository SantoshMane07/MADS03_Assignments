package com.example.assignment5.networking_ui_states

import com.example.assignment5.models.PostModel
import com.example.assignment5.room_db.entities.User

interface ProfileScreenUiState {

    object Initial : ProfileScreenUiState

    object Loading : ProfileScreenUiState

    data class Success(val UsersList: ArrayList<User>) : ProfileScreenUiState

    data class Error(val msg: String, val r_code: Int) : ProfileScreenUiState

}
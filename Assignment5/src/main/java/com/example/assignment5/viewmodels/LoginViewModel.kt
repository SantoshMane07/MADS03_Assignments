package com.example.assignment5.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment5.models.UserModel
import com.example.assignment5.networking_ui_states.LoginScreenUiState
import com.example.assignment5.repo.LoginRepository
import com.example.assignment5.room_db.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    //
    private val _loginScreenUiState: MutableStateFlow<LoginScreenUiState> =
        MutableStateFlow<LoginScreenUiState>(LoginScreenUiState.Initial)
    val loginScreenUiState: StateFlow<LoginScreenUiState> get() = _loginScreenUiState
    //


    fun getRandomUser() {
        viewModelScope.launch {
            //
            _loginScreenUiState.value = LoginScreenUiState.Loading
            loginRepository.getRandomUser(
                onSuccess = { ProfileModel ->
                    _loginScreenUiState.value = LoginScreenUiState.Success(ProfileModel)
                }, onFailure = { errorMsg, statusCode ->
                    _loginScreenUiState.value = LoginScreenUiState.Error(errorMsg, statusCode)
                })
            //
        }
    }
}
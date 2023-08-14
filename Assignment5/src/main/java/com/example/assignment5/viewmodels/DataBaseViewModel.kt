package com.example.assignment5.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_ui_states.ProfileScreenUiState
import com.example.assignment5.repo.DatabaseRepository
import com.example.assignment5.room_db.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DatabaseViewModel @Inject constructor(private val dbRepository: DatabaseRepository) :
    ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users.asStateFlow()

    private var _username = MutableStateFlow<String>("")
    val username: StateFlow<String> get() = _username.asStateFlow()

    //Functions Related To SQLite

    //Insert User
    fun insertUser(user: User) {
        viewModelScope.launch {
            dbRepository.insertUser(user)
        }
    }

    //Get All Users flow list
    fun getAllUsersFlow() = viewModelScope.launch {
        dbRepository.getAllUsersFlow().collect { users ->
            _users.value = users
            _username.value = users[0].userName
        }
    }

    //Get User Id
    fun getUserId() = dbRepository.getUserId()
}
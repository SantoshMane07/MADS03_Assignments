package com.example.assignment3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3.model.repo.DatabaseRepository
import com.example.assignment3.model.room_db.entites.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val dbRepository:DatabaseRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users.asStateFlow()

    //Functions Related To SQLite
    fun inserSingletUser(user: User) {
        viewModelScope.launch {
            dbRepository.inserSingletUser(user)
        }
    }

    fun insertListOfUsers(UsersList:List<User>){
        viewModelScope.launch {
            dbRepository.insertListOfUsers(UsersList)
        }
    }

    fun deleteSingleUser(user: User) {
        viewModelScope.launch {
            dbRepository.deleteSingleUser(user)
        }
    }

    fun getAllUsersFlow() = viewModelScope.launch {
        dbRepository.getAllUsersFlow().collect { users ->
            _users.value = users
        }
    }
    fun getSingleUser(id:Long) =  dbRepository.getSingleUser(id)

    fun getAllUsers(): List<User> = dbRepository.getAllUsers()


    //Related to DataStore
    val authenticated: StateFlow<Boolean> = dbRepository.authenticated.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )
    fun saveAuthenticated(authenticated: Boolean) = viewModelScope.launch {
        dbRepository.saveAuthenticated(authenticated = authenticated)
    }

}
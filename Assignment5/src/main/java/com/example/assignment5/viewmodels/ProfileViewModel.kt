package com.example.assignment5.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assignment5.debug
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_ui_states.ProfileScreenUiState
import com.example.assignment5.repo.DatabaseRepository
import com.example.assignment5.repo.ProfileRepository
import com.example.assignment5.repo.UserAllPostsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    //val posts = profileRepository.getAllUserPosts().cachedIn(viewModelScope)


    private val _posts = MutableStateFlow<PagingData<PostModel>>(PagingData.empty())
    val posts: StateFlow<PagingData<PostModel>> = _posts.asStateFlow()


    suspend fun refreshUserPosts() {

        val freshPosts = profileRepository.getAllUserPosts().cachedIn(viewModelScope)
        _posts.emitAll(freshPosts)
    }
}
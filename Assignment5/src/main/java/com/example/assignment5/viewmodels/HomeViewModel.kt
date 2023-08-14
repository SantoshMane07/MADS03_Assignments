package com.example.assignment5.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment5.models.PostModel
import com.example.assignment5.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    //val posts = homeRepository.getAllPosts().cachedIn(viewModelScope)
    private val _posts = MutableStateFlow<PagingData<PostModel>>(PagingData.empty())
    val posts: StateFlow<PagingData<PostModel>> = _posts.asStateFlow()

    suspend fun refreshPosts() {
        val freshPosts = homeRepository.getAllPosts().cachedIn(viewModelScope)
        _posts.emitAll(freshPosts)
    }
}

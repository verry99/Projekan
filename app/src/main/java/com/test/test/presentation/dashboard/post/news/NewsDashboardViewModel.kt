package com.test.test.presentation.dashboard.post.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.post.news.get_all_news.GetAllNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDashboardViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val state: SavedStateHandle,
) : ViewModel() {

    val token = "Bearer " + state.get<String>("token")!!

    val news = getAllNewsUseCase(token).cachedIn(viewModelScope)
}
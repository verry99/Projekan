package com.test.test.presentation.dashboard.post.opinion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.post.opinion.get_all_opinion.GetAllOpinionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OpinionDashboardViewModel @Inject constructor(
    private val getAllOpinionUseCase: GetAllOpinionUseCase,
    private val state: SavedStateHandle,
) : ViewModel() {

    val token = "Bearer " + state.get<String>("token")!!

    val opinion = getAllOpinionUseCase(token).cachedIn(viewModelScope)
}
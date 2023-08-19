package com.test.test.presentation.dashboard.activities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.post.activities.GetAllActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val getAllActivitiesUseCase: GetAllActivitiesUseCase,
    private val state: SavedStateHandle,
) : ViewModel() {

    val token = "Bearer " + state.get<String>("token")!!

    val activities = getAllActivitiesUseCase(token).cachedIn(viewModelScope)
}
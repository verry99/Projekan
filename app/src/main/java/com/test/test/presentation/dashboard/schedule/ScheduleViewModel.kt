package com.test.test.presentation.dashboard.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.schedule.ScheduleResponseItem
import com.test.test.domain.use_case.schedule.GetActiveScheduleUseCase
import com.test.test.domain.use_case.schedule.GetAllScheduleUseCase
import com.test.test.domain.use_case.schedule.GetInactiveScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getAllScheduleUseCase: GetAllScheduleUseCase,
    private val getActiveScheduleUseCase: GetActiveScheduleUseCase,
    private val getInactiveScheduleUseCase: GetInactiveScheduleUseCase
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _token = MutableLiveData<String>(null)
    val token: LiveData<String> = _token

    var allSchedule: LiveData<PagingData<ScheduleResponseItem>>? = null
    var activeSchedule: LiveData<PagingData<ScheduleResponseItem>>? = null
    var inactiveSchedule: LiveData<PagingData<ScheduleResponseItem>>? = null

    fun fetchSchedules() {
        allSchedule = getAllScheduleUseCase("Bearer " + _token.value!!).cachedIn(viewModelScope)
        activeSchedule =
            getActiveScheduleUseCase("Bearer " + _token.value!!).cachedIn(viewModelScope)
        inactiveSchedule =
            getInactiveScheduleUseCase("Bearer " + _token.value!!).cachedIn(viewModelScope)
    }

    fun setToken(token: String) {
        _token.value = token
    }
}
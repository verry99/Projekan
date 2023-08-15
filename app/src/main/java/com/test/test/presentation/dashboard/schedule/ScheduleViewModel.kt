package com.test.test.presentation.dashboard.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.test.domain.use_case.schedule.GetAllScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getAllScheduleUseCase: GetAllScheduleUseCase,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _token = MutableLiveData<String>(null)
    val token: LiveData<String> = _token

    //
//    var interaction = getAllInteractionUseCase(token, 10).cachedIn(viewModelScope)
//
//    fun fetchInteraction() {
//        interaction = getAllInteractionUseCase(token, 10).cachedIn(viewModelScope)
//    }

    fun setToken(token: String) {
        _token.value = token
    }
}
package com.test.test.presentation.dashboard.schedule.detail_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.schedule.detail.DetailScheduleResponse
import com.test.test.domain.use_case.schedule.GetDetailScheduleUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScheduleViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _schedule = MutableLiveData<DetailScheduleResponse>()
    val schedule: LiveData<DetailScheduleResponse> = _schedule

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val id: String = state["id"]!!


    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().accessToken.let { token ->
                getDetailScheduleUseCase("Bearer $token", id.toInt()).onEach {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { response ->
                                _schedule.value = response
                            }
                        }

                        is Resource.Error -> {
                            _errorMessage.value = it.message!!
                        }

                        is Resource.Loading -> {}
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
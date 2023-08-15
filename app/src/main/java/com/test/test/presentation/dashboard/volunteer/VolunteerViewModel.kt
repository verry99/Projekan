package com.test.test.presentation.dashboard.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import com.test.test.domain.use_case.volunteer.get_all_volunteer.GetAllVolunteerUseCase
import com.test.test.domain.use_case.volunteer.get_summary.GetVolunteerSummaryUseCase
import com.test.test.domain.use_case.volunteer.get_volunteer_by_name.GetVolunteerByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerViewModel @Inject constructor(
    private val getAllVolunteerUseCase: GetAllVolunteerUseCase,
    private val getVolunteerSummaryUseCase: GetVolunteerSummaryUseCase,
    private val getVolunteerByNameUseCase: GetVolunteerByNameUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _volunteerSummary = MutableLiveData<VolunteerSummaryResponse>()
    val volunteerSummary: LiveData<VolunteerSummaryResponse> = _volunteerSummary

    val token = "Bearer " + state.get<String>("token")!!

    var volunteer = getAllVolunteerUseCase(token, 10).cachedIn(viewModelScope)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchVolunteerSummary()
    }

    private fun fetchVolunteerSummary() {
        viewModelScope.launch {
            getVolunteerSummaryUseCase(token).onEach {
                when (it) {
                    is Resource.Success -> {
                        _volunteerSummary.value = it.data!!
                        _isLoading.value = false
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = it.message!!
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun refreshPage() {
        viewModelScope.launch {
            volunteer = getAllVolunteerUseCase(token, 10).cachedIn(viewModelScope)
        }
        fetchVolunteerSummary()
    }

    fun searchVolunteer(name: String): LiveData<PagingData<VolunteerResponseItem>> {
        return getVolunteerByNameUseCase(token, name, "volunteer").cachedIn(viewModelScope)
    }
}
package com.test.test.presentation.dashboard.supporter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import com.test.test.domain.use_case.supporter.get_all_supporter.GetAllSupporterUseCase
import com.test.test.domain.use_case.volunteer.get_summary.GetVolunteerSummaryUseCase
import com.test.test.domain.use_case.volunteer.get_volunteer_by_name.GetVolunteerByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupporterViewModel @Inject constructor(
    private val getVolunteerByNameUseCase: GetVolunteerByNameUseCase,
    private val getVolunteerSummaryUseCase: GetVolunteerSummaryUseCase,
    private val getAllSupporterUseCase: GetAllSupporterUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _volunteerSummary = MutableLiveData<VolunteerSummaryResponse>()
    val volunteerSummary: LiveData<VolunteerSummaryResponse> = _volunteerSummary

    val token = "Bearer " + state.get<String>("token")!!

    val supporter = getAllSupporterUseCase(token, 10).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            getVolunteerSummaryUseCase(token).let {
                _volunteerSummary.value = it
            }
        }
    }

    fun searchVolunteer(name: String): LiveData<PagingData<VolunteerResponseItem>> {
        return getVolunteerByNameUseCase(token, name, "volunteer").cachedIn(viewModelScope)
    }
}
package com.test.test.presentation.dashboard.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.get_all_volunteer.GetAllVolunteerSummaryUseCase
import com.test.test.domain.use_case.volunteer.get_all_volunteer.GetAllVolunteerUseCase
import com.test.test.domain.use_case.volunteer.get_volunteer_by_name.GetVolunteerByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerViewModel @Inject constructor(
    private val getAllVolunteerUseCase: GetAllVolunteerUseCase,
    private val getAllVolunteerSummaryUseCase: GetAllVolunteerSummaryUseCase,
    private val getVolunteerByNameUseCase: GetVolunteerByNameUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _volunteerSummary = MutableLiveData<VolunteerSummaryResponse>()
    val volunteerSummary: LiveData<VolunteerSummaryResponse> = _volunteerSummary

    val token = "Bearer " + state.get<String>("token")!!

    val volunteer = getAllVolunteerUseCase(token, 10).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
            getAllVolunteerSummaryUseCase(token).let {
                _volunteerSummary.value = it
            }
        }
    }

    fun searchVolunteer(name: String): LiveData<PagingData<VolunteerResponseItem>> {
        return getVolunteerByNameUseCase(token, name, "volunteer").cachedIn(viewModelScope)
    }
}
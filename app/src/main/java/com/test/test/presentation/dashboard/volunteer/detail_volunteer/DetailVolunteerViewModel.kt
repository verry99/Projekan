package com.test.test.presentation.dashboard.volunteer.detail_volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Volunteer
import com.test.test.domain.models.SupporterNumber
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.supporter_number.get_all_province_supporter_number.GetAllProvinceSupporterNumberUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.get_detail_volunteer.GetDetailVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailVolunteerViewModel @Inject constructor(
    private val getAllProvinceSupporterNumberUseCase: GetAllProvinceSupporterNumberUseCase,
    private val getDetailVolunteerUseCase: GetDetailVolunteerUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _provinceSupporterNumber = MutableLiveData<List<SupporterNumber>>()
    val provinceSupporterNumber: LiveData<List<SupporterNumber>> = _provinceSupporterNumber

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _volunteer = MutableLiveData<Volunteer>()
    val volunteer: LiveData<Volunteer> = _volunteer

    init {
        viewModelScope.launch {
            getAllProvinceSupporterNumberUseCase().let {
                _provinceSupporterNumber.value = it
            }
            getUserPreferenceUseCase().let { _user.value = it }

            val token = "Bearer " + _user.value!!.accessToken
            val id: String = state["id"]!!
            getDetailVolunteerUseCase(token, id.toInt()).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { response ->
                            _volunteer.value = response.volunteer!!
                        }
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}
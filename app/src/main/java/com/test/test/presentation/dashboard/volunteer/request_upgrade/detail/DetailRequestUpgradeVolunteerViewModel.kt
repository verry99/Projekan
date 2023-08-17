package com.test.test.presentation.dashboard.volunteer.request_upgrade.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.request_upgrade.detail.DetailRequestUpgradeVolunteerResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.request_upgrade.GetDetailRequestUpgradeVolunteerUseCase
import com.test.test.domain.use_case.volunteer.request_upgrade.UpdateRequestUpgradeVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRequestUpgradeVolunteerViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getDetailRequestUpgradeVolunteerUseCase: GetDetailRequestUpgradeVolunteerUseCase,
    private val updateRequestUpgradeVolunteerUseCase: UpdateRequestUpgradeVolunteerUseCase,
    private val state: SavedStateHandle

) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val success = MutableLiveData("")

    private val _user = MutableLiveData<UserPref>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _data = MutableLiveData<DetailRequestUpgradeVolunteerResponse>()
    val data: LiveData<DetailRequestUpgradeVolunteerResponse> = _data

    private val id = state.get<String>("id")

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
            getDetailRequestUpgradeVolunteerUseCase(
                "Bearer ${_user.value!!.accessToken}",
                id!!.toInt()
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _data.value = it.data!!
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

    fun updateStatus(status: String) {
        val token = "Bearer " + _user.value?.accessToken
        viewModelScope.launch {
            updateRequestUpgradeVolunteerUseCase(token, id!!.toInt(), status).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        if (it.data?.data?.status == "accepted") success.value = "accepted"
                        if (it.data?.data?.status == "rejected") success.value = "rejected"
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
}


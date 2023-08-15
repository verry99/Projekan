package com.test.test.presentation.dashboard.other_user.user.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.get_request_upgrade.GetRequestUpgradeVolunteerUseCase
import com.test.test.domain.use_case.volunteer.request_upgrade.RequestUpgradeVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserVolunteerViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getRequestUpgradeVolunteerUseCase: GetRequestUpgradeVolunteerUseCase,
    private val requestUpgradeVolunteerUseCase: RequestUpgradeVolunteerUseCase
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val success = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserPref>()

    private val _pending = MutableLiveData(false)
    val pending: LiveData<Boolean> = _pending

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
                getRequestUpgradeVolunteer()
            }
        }
    }

    private fun getRequestUpgradeVolunteer() {
        val token = "Bearer " + _user.value?.accessToken!!
        viewModelScope.launch {
            getRequestUpgradeVolunteerUseCase(token).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        if (it.data?.data?.status == "pending") {
                            _pending.value = true
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun requestUpgradeVolunteer(
        reason: String,
    ) {
        val token = "Bearer " + _user.value?.accessToken!!
        viewModelScope.launch {
            requestUpgradeVolunteerUseCase(
                token,
                "volunteer",
                reason,
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _pending.value = true
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
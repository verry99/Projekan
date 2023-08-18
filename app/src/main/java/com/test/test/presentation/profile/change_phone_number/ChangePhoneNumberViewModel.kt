package com.test.test.presentation.profile.change_phone_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.profile.UpdatePhoneUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePhoneNumberViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val updatePhoneUseCase: UpdatePhoneUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val success = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
        }
    }

    fun updatePhone(
        password: String,
        phone: String,
    ) {
        viewModelScope.launch {
            updatePhoneUseCase(
                "Bearer ${_user.value?.accessToken}",
                password,
                phone
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        success.value = true
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
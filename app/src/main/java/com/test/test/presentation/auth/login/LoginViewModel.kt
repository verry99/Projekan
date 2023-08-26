package com.test.test.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.use_case.auth.login.LoginUseCase
import com.test.test.domain.use_case.user_pref.save_user.SaveUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveUserPreferenceUseCase: SaveUserPreferenceUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isValid = MutableLiveData(false)
    val isValid: LiveData<Boolean> = _isValid

    fun setIsValid(valid: Boolean) {
        _isValid.value = valid
    }

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _role = MutableLiveData<String>()
    val role: LiveData<String> = _role

    fun login(
        email: String,
        password: String,
        deviceToken: String
    ) {
        loginUseCase(email, password, deviceToken).onEach {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { userData ->
                        _role.value = userData.role
                        saveUserPreferenceUseCase(
                            userData.name,
                            userData.role,
                            userData.urlToImage,
                            userData.accessToken
                        )
                    }
                    _isLoading.value = false
                    _isValid.value = true
                }

                is Resource.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = it.message
                    _isValid.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }
}
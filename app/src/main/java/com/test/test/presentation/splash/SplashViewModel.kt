package com.test.test.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    getUserPreferenceUseCase: GetUserPreferenceUseCase
) : ViewModel() {

    private val _accessToken = MutableLiveData<String>(null)
    val accessToken: LiveData<String> = _accessToken

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _accessToken.value = it.accessToken
            }
        }
    }
}
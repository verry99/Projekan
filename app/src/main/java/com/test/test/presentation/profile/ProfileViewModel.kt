package com.test.test.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.data.remote.dto.profile.ProfileResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.profile.GetProfileUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.user_pref.remove_user.RemoveUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val removeUserPreferenceUseCase: RemoveUserPreferenceUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _profile = MutableLiveData<ProfileResponse>()
    val profile: LiveData<ProfileResponse> = _profile

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _logout = MutableLiveData(false)
    val logout: LiveData<Boolean> = _logout

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { userPref ->
                _user.value = userPref
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                removeUserPreferenceUseCase()
                _logout.value = true
            } catch (e: Exception) {
                Log.e("#vm", "$e")
            }
        }
    }
}
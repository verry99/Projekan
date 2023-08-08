package com.test.test.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.user_pref.remove_user.RemoveUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val removeUserPreferenceUseCase: RemoveUserPreferenceUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            removeUserPreferenceUseCase()
        }
    }
}
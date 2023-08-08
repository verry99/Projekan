package com.test.test.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase
) : ViewModel() {

    private val _hasLoggedIn = MutableLiveData<Boolean>()
    val hasLoggedIn: LiveData<Boolean> = _hasLoggedIn

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _hasLoggedIn.value = it.name.isNotEmpty()
            }
        }
    }
}
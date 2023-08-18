package com.test.test.presentation.analysis.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.models.Profile
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisRegionViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
) : ViewModel() {

    private val _regency = MutableLiveData<List<Regency>>()
    val regency: LiveData<List<Regency>> = _regency

    val selectedProvince = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _user = MutableLiveData<UserPref>()

    val success = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile


    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it

            }
        }
    }
}


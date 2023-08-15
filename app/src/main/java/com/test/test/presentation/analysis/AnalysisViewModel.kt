package com.test.test.presentation.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.analysis.GetAnalysisUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getAnalysisUseCase: GetAnalysisUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _data = MutableLiveData<AnalysisResponse>()
    val data: LiveData<AnalysisResponse> = _data

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { _user.value = it }
            fetchAnalysisData()
        }
    }

    fun fetchAnalysisData() {
        viewModelScope.launch {
            getAnalysisUseCase("Bearer" + _user.value!!.accessToken).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { response ->
                            _data.value = response
                        }
                        _isLoading.value = false
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
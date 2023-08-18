package com.test.test.presentation.dashboard.real_count.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.real_counts.detail.DetailRealCountResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.real_count.GetDetailRealCountUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRealCountViewModel @Inject constructor(
    private val getDetailRealCountUseCase: GetDetailRealCountUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _response = MutableLiveData<DetailRealCountResponse>()
    val response: LiveData<DetailRealCountResponse> = _response

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserPref>()

    private val id = state.get<String>("id")

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().accessToken.let { token ->
                getDetailRealCountUseCase("Bearer $token", id!!.toInt()).onEach {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { response ->
                                _response.value = response
                                _isLoading.value = false
                            }
                        }

                        is Resource.Error -> {
                            _errorMessage.value = it.message!!
                            _isLoading.value = false
                        }

                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
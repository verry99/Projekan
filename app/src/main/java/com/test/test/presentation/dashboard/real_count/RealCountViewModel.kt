package com.test.test.presentation.dashboard.real_count

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.common.Resource
import com.test.test.data.remote.dto.real_counts.RealCountsResponse
import com.test.test.domain.use_case.real_count.GetAllRealCountUseCase
import com.test.test.domain.use_case.real_count.GetRealCountSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealCountViewModel @Inject constructor(
    private val getAllRealCountUseCase: GetAllRealCountUseCase,
    private val getRealCountSummaryUseCase: GetRealCountSummaryUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _realCountSummary = MutableLiveData<RealCountsResponse>()
    val realCountSummary: LiveData<RealCountsResponse> = _realCountSummary

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val token = "Bearer " + state.get<String>("token")!!

    var realCount = getAllRealCountUseCase(token, 10).cachedIn(viewModelScope)

    init {
        fetchRealCountSummary()
    }

    fun fetchRealCount() {
        realCount = getAllRealCountUseCase(token, 10).cachedIn(viewModelScope)
    }

    private fun fetchRealCountSummary() {
        viewModelScope.launch {
            getRealCountSummaryUseCase(token).onEach {
                when (it) {
                    is Resource.Success -> {
                        _realCountSummary.value = it.data!!
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
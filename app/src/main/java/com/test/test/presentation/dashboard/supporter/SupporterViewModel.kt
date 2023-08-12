package com.test.test.presentation.dashboard.supporter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse
import com.test.test.domain.use_case.supporter.get_all_supporter.GetAllSupporterUseCase
import com.test.test.domain.use_case.supporter.get_summary.GetSupporterSummaryUseCase
import com.test.test.domain.use_case.supporter.get_supporter_by_name.GetSupporterByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SupporterViewModel @Inject constructor(
    private val getSupporterSummaryUseCase: GetSupporterSummaryUseCase,
    private val getAllSupporterUseCase: GetAllSupporterUseCase,
    private val getSupporterByNameUseCase: GetSupporterByNameUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _supporterSummary = MutableLiveData<SupporterSummaryResponse>()
    val supporterSummary: LiveData<SupporterSummaryResponse> = _supporterSummary

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val token = "Bearer " + state.get<String>("token")!!

    val supporter = getAllSupporterUseCase(token, 10).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            try {
                getSupporterSummaryUseCase(token).let {
                    _supporterSummary.value = it
                }
            } catch (e: HttpException) {
                _errorMessage.value = "Server Error."
            } catch (e: IOException) {
                _errorMessage.value = "Couldn't reach the server. Check your internet connection!"
            }
        }
    }

    fun searchSupporter(name: String): LiveData<PagingData<SupporterResponseItem>> {
        return getSupporterByNameUseCase(token, name, "supporter").cachedIn(viewModelScope)
    }
}
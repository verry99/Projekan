package com.test.test.presentation.dashboard.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import com.test.test.domain.use_case.volunteer.get_all_volunteer.GetAllVolunteerUseCase
import com.test.test.domain.use_case.volunteer.get_summary.GetVolunteerSummaryUseCase
import com.test.test.domain.use_case.volunteer.get_volunteer_by_name.GetVolunteerByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class VolunteerViewModel @Inject constructor(
    private val getAllVolunteerUseCase: GetAllVolunteerUseCase,
    private val getVolunteerSummaryUseCase: GetVolunteerSummaryUseCase,
    private val getVolunteerByNameUseCase: GetVolunteerByNameUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _volunteerSummary = MutableLiveData<VolunteerSummaryResponse>()
    val volunteerSummary: LiveData<VolunteerSummaryResponse> = _volunteerSummary

    val token = "Bearer " + state.get<String>("token")!!

    val volunteer = getAllVolunteerUseCase(token, 10).cachedIn(viewModelScope)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            try {
                getVolunteerSummaryUseCase(token).let {
                    _volunteerSummary.value = it
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    in 400..499 -> {
                        _errorMessage.value = "Token expired. Silahkan login kembali!"
                    }

                    in 500..599 -> {
                        _errorMessage.value = "Server Error."
                    }

                    else -> {
                        _errorMessage.value = e.localizedMessage ?: "Unexpected Error"
                    }
                }
            } catch (e: IOException) {
                _errorMessage.value = "Couldn't reach the server. Check your internet connection!"
            }
        }
    }

    fun searchVolunteer(name: String): LiveData<PagingData<VolunteerResponseItem>> {
        return getVolunteerByNameUseCase(token, name, "volunteer").cachedIn(viewModelScope)
    }
}
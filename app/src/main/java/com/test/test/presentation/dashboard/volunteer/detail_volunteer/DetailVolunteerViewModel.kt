package com.test.test.presentation.dashboard.volunteer.detail_volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.domain.models.SupporterNumber
import com.test.test.domain.use_case.supporter_number.get_all_province_supporter_number.GetAllProvinceSupporterNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailVolunteerViewModel @Inject constructor(
    private val getAllProvinceSupporterNumberUseCase: GetAllProvinceSupporterNumberUseCase
) : ViewModel() {

    private val _provinceSupporterNumber = MutableLiveData<List<SupporterNumber>>()
    val provinceSupporterNumber: LiveData<List<SupporterNumber>> = _provinceSupporterNumber

    init {
        viewModelScope.launch {
            getAllProvinceSupporterNumberUseCase().let {
                _provinceSupporterNumber.value = it
            }
        }
    }
}
package com.test.test.presentation.dashboard.volunteer.approval_volunteer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.volunteer.request_upgrade.GetAllRequestUpgradeVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VolunteerApprovalViewModel @Inject constructor(
    private val getAllRequestUpgradeVolunteerUseCase: GetAllRequestUpgradeVolunteerUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    val token = "Bearer " + state.get<String>("token")!!

    var volunteer = getAllRequestUpgradeVolunteerUseCase(token, 10).cachedIn(viewModelScope)
}
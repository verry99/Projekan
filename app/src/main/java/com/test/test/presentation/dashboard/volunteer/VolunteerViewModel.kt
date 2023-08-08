package com.test.test.presentation.dashboard.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.get_all_volunteer.GetVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerViewModel @Inject constructor(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    val volunteer: LiveData<PagingData<VolunteerResponseItem>> = liveData {
        val accessToken = state.get<String>("token")
        val pagingData = getVolunteerUseCase("Bearer $accessToken", 10).cachedIn(viewModelScope)
        emitSource(pagingData)
    }

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
        }
    }
}
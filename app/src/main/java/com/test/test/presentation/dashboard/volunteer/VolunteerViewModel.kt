package com.test.test.presentation.dashboard.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.get_all_volunteer.GetVolunteerUseCase
import com.test.test.domain.use_case.volunteer.get_volunteer_by_name.GetVolunteerByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerViewModel @Inject constructor(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getVolunteerByNameUseCase: GetVolunteerByNameUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    val token = "Bearer " + state.get<String>("token")!!

    val volunteer = getVolunteerUseCase(token, 10).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
        }
    }

    fun searchVolunteer(name: String): LiveData<PagingData<VolunteerResponseItem>> {
        return getVolunteerByNameUseCase(token, name, "volunteer").cachedIn(viewModelScope)
    }
}
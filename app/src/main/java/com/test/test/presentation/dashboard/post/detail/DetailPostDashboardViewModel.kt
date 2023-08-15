package com.test.test.presentation.dashboard.post.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.post.detail.DetailPostResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.post.detail.GetDetailPostUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPostDashboardViewModel @Inject constructor(
    private val getDetailPostUseCase: GetDetailPostUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _post = MutableLiveData<DetailPostResponse>()
    val post: LiveData<DetailPostResponse> = _post

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { _user.value = it }

            val token = "Bearer " + _user.value!!.accessToken
            getDetailPostUseCase(token, state["slug"]!!).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { response ->
                            _post.value = response
                        }
                    }

                    is Resource.Error -> {
                        _errorMessage.value = it.message!!
                    }

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}
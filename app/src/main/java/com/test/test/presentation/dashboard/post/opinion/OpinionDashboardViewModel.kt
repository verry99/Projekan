package com.test.test.presentation.dashboard.post.opinion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.Post
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.post.opinion.get_all_opinion.GetAllOpinionUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpinionDashboardViewModel @Inject constructor(
    private val getAllOpinionUseCase: GetAllOpinionUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _opinion = MutableLiveData<List<Post>>()
    val opinion: LiveData<List<Post>> = _opinion

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { _user.value = it }
            getAllOpinionUseCase("Bearer " + _user.value!!.accessToken).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { opinion ->
                            _opinion.value = opinion
                        }
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}
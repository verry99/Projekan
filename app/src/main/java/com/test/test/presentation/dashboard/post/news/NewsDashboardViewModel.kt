package com.test.test.presentation.dashboard.post.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.Post
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.post.news.get_all_news.GetAllNewsUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDashboardViewModel @Inject constructor(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _news = MutableLiveData<List<Post>>()
    val news: LiveData<List<Post>> = _news

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { _user.value = it }
            getAllNewsUseCase("Bearer " + _user.value!!.accessToken).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { news ->
                            _news.value = news
                        }
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}
package com.test.test.presentation.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.dashboard.toModel
import com.test.test.domain.models.Banner
import com.test.test.domain.models.Post
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.dashboard.GetDashboardUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getDashboardUseCase: GetDashboardUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()
    val user: LiveData<UserPref> = _user

    private val _news = MutableLiveData<List<Post>>()
    val news: LiveData<List<Post>> = _news

    private val _opinion = MutableLiveData<List<Post>>()
    val opinion: LiveData<List<Post>> = _opinion

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = _banners

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { _user.value = it }
            getDashboardUseCase("Bearer" + _user.value!!.accessToken).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { response ->
                            _banners.value = response.data!!.banners!!.map { it.toModel() }
                            _news.value = response.data.posts!!.berita!!.map { it.toModel() }
                            _opinion.value = response.data.posts.opini!!.map { it.toModel() }
                        }
                    }

                    is Resource.Error -> {}

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}
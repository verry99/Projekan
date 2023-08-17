package com.test.test.presentation.dashboard.real_count.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.interaction.add_interaction.AddInteractionUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddRealCountViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val addInteractionUseCase: AddInteractionUseCase
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val success = MutableLiveData(false)

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserPref>()

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let {
                _user.value = it
            }
        }
    }

    fun addInteraction(
        photo: MultipartBody.Part,
        title: RequestBody,
        description: RequestBody,
    ) {
        val token = "Bearer " + _user.value?.accessToken!!
        viewModelScope.launch {
            addInteractionUseCase(
                token,
                photo,
                title,
                description
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        success.value = true
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = it.message!!
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                }
            }.launchIn(viewModelScope)
        }
    }
}
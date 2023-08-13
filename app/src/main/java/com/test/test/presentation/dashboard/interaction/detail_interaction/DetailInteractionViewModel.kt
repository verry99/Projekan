package com.test.test.presentation.dashboard.interaction.detail_interaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.common.Resource
import com.test.test.data.remote.dto.interaction.detail_interaction.Interaction
import com.test.test.domain.use_case.interaction.add_comment.AddInteractionCommentUseCase
import com.test.test.domain.use_case.interaction.get_detail_interaction.GetDetailInteractionUseCase
import com.test.test.domain.use_case.interaction.get_detail_interaction.GetInteractionCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailInteractionViewModel @Inject constructor(
    private val getDetailInteractionUseCase: GetDetailInteractionUseCase,
    private val getInteractionCommentUseCase: GetInteractionCommentUseCase,
    private val addInteractionCommentUseCase: AddInteractionCommentUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _interaction = MutableLiveData<Interaction>()
    val interaction: LiveData<Interaction> = _interaction

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val id: String = state["id"]!!
    private val token: String = "Bearer " + state["token"]!!

    var comment = getInteractionCommentUseCase(token, id.toInt(), 10).cachedIn(viewModelScope)

    private val _addCommentSuccess = MutableLiveData(false)
    val addCommentSuccess: LiveData<Boolean> = _addCommentSuccess

    private val _addCommentIsLoading = MutableLiveData(false)
    val addCommentIsLoading: LiveData<Boolean> = _addCommentIsLoading

    fun setAddCommentSuccess(success: Boolean) {
        _addCommentSuccess.value = success
    }

    fun fetchComment() {
        comment = getInteractionCommentUseCase(token, id.toInt(), 10).cachedIn(viewModelScope)
    }

    init {
        viewModelScope.launch {
            getDetailInteractionUseCase(token, id.toInt()).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { response ->
                            _interaction.value = response.data!!
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

    fun addComment(body: String) {
        viewModelScope.launch {
            addInteractionCommentUseCase(token, _interaction.value?.id!!, body).onEach {
                when (it) {
                    is Resource.Success -> {
                        _addCommentIsLoading.value = false
                        _addCommentSuccess.value = true
                    }

                    is Resource.Error -> {
                        _addCommentIsLoading.value = false
                        _errorMessage.value = it.message!!
                        _addCommentSuccess.value = false
                    }

                    is Resource.Loading -> {
                        _addCommentIsLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
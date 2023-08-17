package com.test.test.presentation.dashboard.real_count

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.interaction.get_all_interaction.GetAllInteractionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealCountViewModel @Inject constructor(
    private val getAllInteractionUseCase: GetAllInteractionUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val token = "Bearer " + state.get<String>("token")!!

    var tps = getAllInteractionUseCase(token, 10).cachedIn(viewModelScope)

    fun fetchInteraction() {
        tps = getAllInteractionUseCase(token, 10).cachedIn(viewModelScope)
    }
}
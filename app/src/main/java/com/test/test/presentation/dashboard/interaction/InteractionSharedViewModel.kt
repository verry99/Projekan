package com.test.test.presentation.dashboard.interaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class InteractionSharedViewModel : ViewModel() {

    private val _fragmentAddInteractionFinished = MutableLiveData<Boolean>()
    val fragmentAddInteractionFinished: LiveData<Boolean> = _fragmentAddInteractionFinished

    fun setFragmentAddInteractionFinished() {
        _fragmentAddInteractionFinished.value = true
    }
}
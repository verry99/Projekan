package com.test.test.presentation.dashboard.notification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.notification.GetAllNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getAllNotificationUseCase: GetAllNotificationUseCase,
    private val state: SavedStateHandle,
) : ViewModel() {

    val token = "Bearer " + state.get<String>("token")!!

    var notification = getAllNotificationUseCase(token).cachedIn(viewModelScope)

    fun fetchNotification() {
        notification = getAllNotificationUseCase(token).cachedIn(viewModelScope)
    }
}
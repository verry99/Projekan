package com.test.test.domain.use_case.notification

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.notification.NotificationResponseItem
import com.test.test.domain.repository.DashboardRepository
import javax.inject.Inject

class GetAllNotificationUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {
    operator fun invoke(token: String): LiveData<PagingData<NotificationResponseItem>> {
        return dashboardRepository.getAllNotification(token)
    }
}
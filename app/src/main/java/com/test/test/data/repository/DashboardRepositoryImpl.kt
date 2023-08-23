package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.data.remote.dto.notification.NotificationResponseItem
import com.test.test.data.remote.dto.notification.detail.DetailNotificationResponse
import com.test.test.data.remote.paging_source.NotificationPagingSource
import com.test.test.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : DashboardRepository {

    override suspend fun getDashboard(token: String): DashboardResponse {
        return dashboardService.getDashboard(token)
    }

    override fun getAllNotification(token: String): LiveData<PagingData<NotificationResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                NotificationPagingSource(dashboardService, token)
            }
        ).liveData
    }

    override suspend fun getDetailNotification(token: String, id: Int): DetailNotificationResponse {
        return dashboardService.getDetailNotification(token, id)
    }
}
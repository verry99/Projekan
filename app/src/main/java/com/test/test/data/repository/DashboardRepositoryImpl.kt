package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : DashboardRepository {

    override suspend fun getDashboard(token: String): DashboardResponse {
        return dashboardService.getDashboard(token)
    }
}
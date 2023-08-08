package com.test.test.domain.repository

import com.test.test.data.remote.dto.dashboard.DashboardResponse

interface DashboardRepository {
    suspend fun getDashboard(token: String): DashboardResponse
}
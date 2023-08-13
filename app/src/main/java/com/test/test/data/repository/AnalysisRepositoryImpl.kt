package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.domain.repository.AnalysisRepository
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : AnalysisRepository {
    override suspend fun getAnalysis(token: String): AnalysisResponse {
        return dashboardService.getAnalysis(token)
    }
}
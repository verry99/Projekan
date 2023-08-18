package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisGetAreaItemsResponse
import com.test.test.domain.repository.AnalysisRepository
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : AnalysisRepository {
    override suspend fun getAnalysis(token: String): AnalysisResponse {
        return dashboardService.getAnalysis(token)
    }

    override suspend fun getAnalysisArea(token: String): AnalysisGetAreaResponse {
        return dashboardService.getAnalysisArea(token)
    }

    override suspend fun getAnalysisAreaItems(
        token: String,
        area: String,
        type: String
    ): AnalysisGetAreaItemsResponse {
        return dashboardService.getAnalysisAreaItems(token, area, type)
    }
}
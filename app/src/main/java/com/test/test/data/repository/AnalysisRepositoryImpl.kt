package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponseItem
import com.test.test.data.remote.paging_source.AnalysisByAreaPagingSource
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

    override suspend fun getAnalysisDataByAreaSummary(
        token: String,
        area: String,
        type: String,
        role: String,
        page: Int
    ): AnalysisDataByAreaResponse {
        return dashboardService.getAnalysisDataByArea(token, area, type, role, page)
    }

    override fun getAnalysisDataByArea(
        token: String,
        area: String,
        type: String,
        role: String
    ): LiveData<PagingData<AnalysisDataByAreaResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                AnalysisByAreaPagingSource(dashboardService, token, area, type, role)
            }
        ).liveData
    }
}
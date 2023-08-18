package com.test.test.domain.repository

import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisGetAreaItemsResponse

interface AnalysisRepository {
    suspend fun getAnalysis(token: String): AnalysisResponse

    suspend fun getAnalysisArea(token: String): AnalysisGetAreaResponse

    suspend fun getAnalysisAreaItems(
        token: String,
        area:String,
        type: String,
    ): AnalysisGetAreaItemsResponse
}
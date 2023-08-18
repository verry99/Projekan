package com.test.test.domain.repository

import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse

interface AnalysisRepository {
    suspend fun getAnalysis(token: String): AnalysisResponse

    suspend fun getAnalysisArea(token: String): AnalysisGetAreaResponse

    suspend fun getAnalysisDataByArea(
        token: String,
        area:String,
        type: String,
    ): AnalysisDataByAreaResponse
}
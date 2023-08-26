package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponseItem

interface AnalysisRepository {

    suspend fun getAnalysis(token: String): AnalysisResponse

    suspend fun getAnalysisArea(token: String): AnalysisGetAreaResponse

    suspend fun getAnalysisDataByAreaSummary(
        token: String,
        area: String,
        type: String,
        role: String,
        page: Int,
    ): AnalysisDataByAreaResponse

    fun getAnalysisDataByArea(
        token: String,
        area: String,
        type: String,
        role: String,
    ): LiveData<PagingData<AnalysisDataByAreaResponseItem>>
}
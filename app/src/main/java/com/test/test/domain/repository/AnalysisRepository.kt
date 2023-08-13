package com.test.test.domain.repository

import com.test.test.data.remote.dto.analysis.AnalysisResponse

interface AnalysisRepository {
    suspend fun getAnalysis(token: String) : AnalysisResponse
}
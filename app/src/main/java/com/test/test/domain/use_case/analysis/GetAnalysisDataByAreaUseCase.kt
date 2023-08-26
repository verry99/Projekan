package com.test.test.domain.use_case.analysis

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponseItem
import com.test.test.domain.repository.AnalysisRepository
import javax.inject.Inject

class GetAnalysisDataByAreaUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    operator fun invoke(
        token: String,
        area: String,
        type: String,
        role: String,
    ): LiveData<PagingData<AnalysisDataByAreaResponseItem>> {
        return analysisRepository.getAnalysisDataByArea(token, area, type, role)
    }
}
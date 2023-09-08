package com.test.test.domain.use_case.division.get_all_tps

import com.test.test.data.remote.dto.region.TpsResponse
import com.test.test.domain.repository.DashboardRepository
import javax.inject.Inject

class GetAllTpsUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(token: String): TpsResponse {
        return dashboardRepository.getAllTps(token)
    }
}
package com.test.test.domain.use_case.supporter.get_summary

import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse
import com.test.test.domain.repository.SupporterRepository
import javax.inject.Inject

class GetSupporterSummaryUseCase @Inject constructor(
    private val supporterRepository: SupporterRepository
) {
    suspend operator fun invoke(token: String): SupporterSummaryResponse {
        return supporterRepository.getSupporterSummary(token)
    }
}
package com.test.test.domain.use_case.volunteer.get_summary

import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import com.test.test.domain.repository.VolunteerRepository
import javax.inject.Inject

class GetVolunteerSummaryUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    suspend operator fun invoke(token: String): VolunteerSummaryResponse {
        return volunteerRepository.getAllVolunteerSummary(token)
    }
}
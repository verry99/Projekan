package com.test.test.domain.use_case.division.get_all_regency

import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class GetAllRegencyUseCase @Inject constructor(
    private val divisionRepository: DivisionRepository
) {
    suspend operator fun invoke(provinceId: String): List<Regency> {
        return divisionRepository.getAllRegency(provinceId).map { it.toModel() }
    }
}
package com.test.test.domain.use_case.division.get_all_province

import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Division.Province
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class GetAllProvinceUseCase @Inject constructor(
    private val divisionRepository: DivisionRepository
) {
    suspend operator fun invoke(): List<Province> {
        return divisionRepository.getAllProvince().map { it.toModel() }
    }
}
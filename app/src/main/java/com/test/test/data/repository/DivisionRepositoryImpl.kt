package com.test.test.data.repository

import com.test.test.data.remote.api.DivisionService
import com.test.test.data.remote.dto.region.SubDistrictDTO
import com.test.test.data.remote.dto.region.ProvinceDTO
import com.test.test.data.remote.dto.region.RegencyDTO
import com.test.test.data.remote.dto.region.VillageDTO
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class DivisionRepositoryImpl @Inject constructor(
    private val divisionService: DivisionService,
) : DivisionRepository {
    override suspend fun getAllProvince(): List<ProvinceDTO> {
        return divisionService.getAllProvince()
    }

    override suspend fun getAllRegency(provinceId: String): List<RegencyDTO> {
        return divisionService.getAllRegency(provinceId)
    }

    override suspend fun getAllSubDistrict(regencyId: String): List<SubDistrictDTO> {
        return divisionService.getAllDistrict(regencyId)
    }

    override suspend fun getAllVillage(districtId: String): List<VillageDTO> {
        return divisionService.getAllSubDistrict(districtId)
    }
}
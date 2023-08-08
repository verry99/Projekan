package com.test.test.data.repository

import com.test.test.data.remote.api.RegionService
import com.test.test.data.remote.dto.region.DistrictDTO
import com.test.test.data.remote.dto.region.ProvinceDTO
import com.test.test.data.remote.dto.region.RegencyDTO
import com.test.test.data.remote.dto.region.VillageDTO
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class DivisionRepositoryImpl @Inject constructor(
    private val regionService: RegionService
) : DivisionRepository {
    override suspend fun getAllProvince(): List<ProvinceDTO> {
        return regionService.getAllProvince()
    }

    override suspend fun getAllRegency(provinceId: String): List<RegencyDTO> {
        return regionService.getAllRegency(provinceId)
    }

    override suspend fun getAllDistrict(regencyId: String): List<DistrictDTO> {
        return regionService.getAllDistrict(regencyId)
    }

    override suspend fun getAllVillage(districtId: String): List<VillageDTO> {
        return regionService.getAllSubDistrict(districtId)
    }
}
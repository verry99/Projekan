package com.test.test.domain.repository

import com.test.test.data.remote.dto.region.DistrictDTO
import com.test.test.data.remote.dto.region.ProvinceDTO
import com.test.test.data.remote.dto.region.RegencyDTO
import com.test.test.data.remote.dto.region.VillageDTO

interface DivisionRepository {
    suspend fun getAllProvince(): List<ProvinceDTO>
    suspend fun getAllRegency(provinceId: String): List<RegencyDTO>
    suspend fun getAllDistrict(regencyId: String): List<DistrictDTO>
    suspend fun getAllVillage(districtId: String): List<VillageDTO>
}
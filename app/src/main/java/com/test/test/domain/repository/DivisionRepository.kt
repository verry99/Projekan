package com.test.test.domain.repository

import com.test.test.data.remote.dto.region.SubDistrictDTO
import com.test.test.data.remote.dto.region.ProvinceDTO
import com.test.test.data.remote.dto.region.RegencyDTO
import com.test.test.data.remote.dto.region.VillageDTO

interface DivisionRepository {
    suspend fun getAllProvince(): List<ProvinceDTO>
    suspend fun getAllRegency(provinceId: String): List<RegencyDTO>
    suspend fun getAllSubDistrict(regencyId: String): List<SubDistrictDTO>
    suspend fun getAllVillage(districtId: String): List<VillageDTO>
}
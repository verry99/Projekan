package com.test.test.data.remote.api

import com.test.test.data.remote.dto.region.DistrictDTO
import com.test.test.data.remote.dto.region.ProvinceDTO
import com.test.test.data.remote.dto.region.RegencyDTO
import com.test.test.data.remote.dto.region.VillageDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RegionService {
    @GET("provinces.json")
    suspend fun getAllProvince(): List<ProvinceDTO>

    @GET("regencies/{provinceId}.json")
    suspend fun getAllRegency(
        @Path("provinceId") provinceId: String
    ): List<RegencyDTO>

    @GET("districts/{regencyId}.json")
    suspend fun getAllDistrict(
        @Path("regencyId") regencyId: String
    ): List<DistrictDTO>

    @GET("villages/{districtId}.json")
    suspend fun getAllSubDistrict(
        @Path("districtId") districtId: String
    ): List<VillageDTO>
}
package com.test.test.data.remote.dto.region

import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Division.SubDistrict

data class SubDistrictDTO(
    @field:SerializedName("regency_id")
    val regencyId: String,
    val name: String,
    val id: String
)

fun SubDistrictDTO.toModel(): SubDistrict {
    return SubDistrict(
        id = id,
        name = name
    )
}
package com.test.test.data.remote.dto.region

import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Division.District

data class DistrictDTO(
    @field:SerializedName("regency_id")
    val regencyId: String,
    val name: String,
    val id: String
)

fun DistrictDTO.toModel(): District {
    return District(
        id = id,
        name = name
    )
}
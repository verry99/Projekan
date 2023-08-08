package com.test.test.data.remote.dto.region

import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Division.Village

data class VillageDTO(
    @field:SerializedName("district_id")
    val districtId: String,
    val id: String,
    val name: String,
)

fun VillageDTO.toModel(): Village {
    return Village(
        id = id,
        name = name
    )
}


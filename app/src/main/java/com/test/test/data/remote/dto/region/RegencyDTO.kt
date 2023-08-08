package com.test.test.data.remote.dto.region

import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Division.Regency

data class RegencyDTO(
    @field:SerializedName("province_id")
    val provinceId: String,
    val name: String,
    val id: String
)

fun RegencyDTO.toModel(): Regency {
    return Regency(id = id, name = name)
}
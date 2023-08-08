package com.test.test.data.remote.dto.region

import com.test.test.domain.models.Division.Province

data class ProvinceDTO(
    val name: String,
    val id: String
)

fun ProvinceDTO.toModel(): Province {
    return Province(id = id, name = name)
}
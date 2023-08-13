package com.test.test.data.remote.dto.volunteer.detail_volunteer

import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("gender")
    val gender: Gender?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("total")
    val total: Int?
)
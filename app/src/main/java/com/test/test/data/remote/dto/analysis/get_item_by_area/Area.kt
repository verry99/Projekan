package com.test.test.data.remote.dto.analysis.get_item_by_area


import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("gender")
    val gender: Gender?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("total")
    val total: Int?
)
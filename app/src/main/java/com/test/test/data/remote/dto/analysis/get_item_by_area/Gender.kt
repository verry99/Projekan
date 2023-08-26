package com.test.test.data.remote.dto.analysis.get_item_by_area


import com.google.gson.annotations.SerializedName

data class Gender(
    @SerializedName("L")
    val l: Int?,
    @SerializedName("P")
    val p: Int?
)
package com.test.test.data.remote.dto.analysis.get_item_by_area


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("allSupporter")
    val allSupporter: AllSupporter?,
    @SerializedName("area")
    val area: List<Area>?
)
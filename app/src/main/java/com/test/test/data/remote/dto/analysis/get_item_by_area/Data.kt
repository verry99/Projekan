package com.test.test.data.remote.dto.analysis.get_item_by_area


import com.google.gson.annotations.SerializedName
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Area

data class Data(
    @SerializedName("allSupporter")
    val allSupporter: AllSupporter,
    @SerializedName("area")
    val area: List<Area>
)
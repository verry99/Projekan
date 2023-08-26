package com.test.test.data.remote.dto.analysis.get_item_by_area


import com.google.gson.annotations.SerializedName
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Area

data class AnalysisDataByAreaResponse(
    @SerializedName("area")
    val area: List<Area>?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
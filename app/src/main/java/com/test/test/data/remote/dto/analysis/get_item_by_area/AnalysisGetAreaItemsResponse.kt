package com.test.test.data.remote.dto.analysis.get_item_by_area


import com.google.gson.annotations.SerializedName

data class AnalysisGetAreaItemsResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
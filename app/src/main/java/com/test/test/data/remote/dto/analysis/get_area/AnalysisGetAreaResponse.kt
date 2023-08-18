package com.test.test.data.remote.dto.analysis.get_area


import com.google.gson.annotations.SerializedName

data class AnalysisGetAreaResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
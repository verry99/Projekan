package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName

data class AnalysisResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
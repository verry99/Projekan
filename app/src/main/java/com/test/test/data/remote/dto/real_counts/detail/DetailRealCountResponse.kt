package com.test.test.data.remote.dto.real_counts.detail


import com.google.gson.annotations.SerializedName

data class DetailRealCountResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
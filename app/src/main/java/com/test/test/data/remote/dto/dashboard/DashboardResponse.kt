package com.test.test.data.remote.dto.dashboard


import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
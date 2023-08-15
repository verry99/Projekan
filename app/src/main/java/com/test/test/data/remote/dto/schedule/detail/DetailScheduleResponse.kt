package com.test.test.data.remote.dto.schedule.detail


import com.google.gson.annotations.SerializedName

data class DetailScheduleResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
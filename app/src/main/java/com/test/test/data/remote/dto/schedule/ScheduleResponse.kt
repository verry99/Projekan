package com.test.test.data.remote.dto.schedule


import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
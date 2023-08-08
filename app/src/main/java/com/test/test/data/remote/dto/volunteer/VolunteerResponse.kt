package com.test.test.data.remote.dto.volunteer


import com.google.gson.annotations.SerializedName

data class VolunteerResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
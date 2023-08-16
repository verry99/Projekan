package com.test.test.data.remote.dto.volunteer.request_upgrade


import com.google.gson.annotations.SerializedName

data class RequestUpgradeVolunteerResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
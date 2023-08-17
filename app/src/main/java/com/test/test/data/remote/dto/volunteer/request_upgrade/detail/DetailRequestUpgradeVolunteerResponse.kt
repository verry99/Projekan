package com.test.test.data.remote.dto.volunteer.request_upgrade.detail


import com.google.gson.annotations.SerializedName

data class DetailRequestUpgradeVolunteerResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
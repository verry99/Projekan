package com.test.test.data.remote.dto.user.request_upgrade_volunteer


import com.google.gson.annotations.SerializedName

data class RequestUpgradeVolunteerStatusResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
package com.test.test.data.remote.dto.volunteer.request_upgrade.accept_reject


import com.google.gson.annotations.SerializedName

data class UpdateRequestUpgradeVolunteerResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
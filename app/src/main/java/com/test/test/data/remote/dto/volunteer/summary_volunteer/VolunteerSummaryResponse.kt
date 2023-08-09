package com.test.test.data.remote.dto.volunteer.summary_volunteer


import com.google.gson.annotations.SerializedName

data class VolunteerSummaryResponse(
    @SerializedName("request_upgrade_count")
    val requestUpgradeCount: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_volunteer")
    val totalVolunteer: Int
)
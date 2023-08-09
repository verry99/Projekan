package com.test.test.data.remote.dto.volunteer.detail_volunteer

import com.google.gson.annotations.SerializedName

data class DetailVolunteerResponse(
    @SerializedName("data")
    val volunteer: Volunteer?,
    @SerializedName("status")
    val status: String?
)
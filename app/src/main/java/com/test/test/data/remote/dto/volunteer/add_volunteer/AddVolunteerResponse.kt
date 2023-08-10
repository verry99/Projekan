package com.test.test.data.remote.dto.volunteer.add_volunteer


import com.google.gson.annotations.SerializedName

data class AddVolunteerResponse(
    @SerializedName("account")
    val account: Account,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)
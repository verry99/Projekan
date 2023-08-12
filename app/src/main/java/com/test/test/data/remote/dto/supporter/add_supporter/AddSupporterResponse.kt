package com.test.test.data.remote.dto.supporter.add_supporter


import com.google.gson.annotations.SerializedName

data class AddSupporterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)
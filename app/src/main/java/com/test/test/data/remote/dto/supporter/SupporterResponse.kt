package com.test.test.data.remote.dto.supporter


import com.google.gson.annotations.SerializedName

data class SupporterResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
package com.test.test.data.remote.dto.real_counts.add


import com.google.gson.annotations.SerializedName

data class AddRealCountResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
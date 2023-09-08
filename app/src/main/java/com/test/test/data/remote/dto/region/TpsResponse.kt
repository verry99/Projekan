package com.test.test.data.remote.dto.region


import com.google.gson.annotations.SerializedName

data class TpsResponse(
    @SerializedName("data")
    val tps: List<String>,
    @SerializedName("status")
    val status: String
)
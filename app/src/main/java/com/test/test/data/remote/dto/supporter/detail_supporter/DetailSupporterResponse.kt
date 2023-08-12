package com.test.test.data.remote.dto.supporter.detail_supporter


import com.google.gson.annotations.SerializedName

data class DetailSupporterResponse(
    @SerializedName("data")
    val data: Supporter,
    @SerializedName("status")
    val status: String
)
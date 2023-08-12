package com.test.test.data.remote.dto.supporter.summary_supporter


import com.google.gson.annotations.SerializedName

data class SupporterSummaryResponse(
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_suporter")
    val totalSupporter: Int
)
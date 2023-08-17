package com.test.test.data.remote.dto.real_counts


import com.google.gson.annotations.SerializedName

data class RealCountsResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("rivals")
    val rivals: Rivals?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("suporter")
    val suporter: Int?,
    @SerializedName("total_vote")
    val totalVote: Int?,
    @SerializedName("voter_percentage")
    val voterPercentage: Double?
)
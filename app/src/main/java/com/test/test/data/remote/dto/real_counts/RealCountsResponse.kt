package com.test.test.data.remote.dto.real_counts


import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Rival

data class RealCountsResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("rivals")
    val rivals: List<Rival>,
    @SerializedName("suporter")
    val supporter: Int?,
    @SerializedName("total_vote")
    val totalVote: Int?,
    @SerializedName("voter_percentage")
    val voterPercentage: Double?,
)
package com.test.test.data.remote.dto.interaction.detail_interaction

import com.google.gson.annotations.SerializedName

data class DetailInteractionResponse(
    @SerializedName("data")
    val `data`: Interaction?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
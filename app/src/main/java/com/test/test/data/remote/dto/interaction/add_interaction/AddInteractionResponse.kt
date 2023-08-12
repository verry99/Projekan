package com.test.test.data.remote.dto.interaction.add_interaction


import com.google.gson.annotations.SerializedName

data class AddInteractionResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
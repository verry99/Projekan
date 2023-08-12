package com.test.test.data.remote.dto.interaction


import com.google.gson.annotations.SerializedName

data class InteractionResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: String?
)
package com.test.test.data.remote.dto.interaction.detail_interaction.add_comment


import com.google.gson.annotations.SerializedName

data class AddCommentResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
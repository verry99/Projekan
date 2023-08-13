package com.test.test.data.remote.dto.interaction.detail_interaction.add_comment


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("author")
    val author: String?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("interaction_id")
    val interactionId: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: Int?
)
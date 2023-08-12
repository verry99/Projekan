package com.test.test.data.remote.dto.interaction.detail_interaction


import com.google.gson.annotations.SerializedName

data class InteractionCommentResponseItem(
    @SerializedName("body")
    val body: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("interaction_id")
    val interactionId: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("user_id")
    val userId: String?
)
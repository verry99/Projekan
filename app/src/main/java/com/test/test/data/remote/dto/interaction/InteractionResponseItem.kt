package com.test.test.data.remote.dto.interaction


import com.google.gson.annotations.SerializedName

data class InteractionResponseItem(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("user_id")
    val userId: String?
)
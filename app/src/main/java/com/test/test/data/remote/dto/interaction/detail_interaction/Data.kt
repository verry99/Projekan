package com.test.test.data.remote.dto.interaction.detail_interaction


import com.google.gson.annotations.SerializedName

data class Interaction(
    @SerializedName("author")
    val author: String?,
    @SerializedName("comment")
    val comment: Comment?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("created_at_human")
    val createdAtHuman: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("interaction_comment_count")
    val interactionCommentCount: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("updated_at_human")
    val updatedAtHuman: String?,
    @SerializedName("user_id")
    val userId: String?
)
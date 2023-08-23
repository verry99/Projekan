package com.test.test.data.remote.dto.notification


import com.google.gson.annotations.SerializedName

data class NotificationResponseItem(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Any?,
    @SerializedName("is_read")
    val isRead: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("url")
    val url: Any?,
    @SerializedName("user_id")
    val userId: String?
)
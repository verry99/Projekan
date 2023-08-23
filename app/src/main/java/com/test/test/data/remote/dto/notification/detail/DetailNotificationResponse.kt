package com.test.test.data.remote.dto.notification.detail


import com.google.gson.annotations.SerializedName

data class DetailNotificationResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
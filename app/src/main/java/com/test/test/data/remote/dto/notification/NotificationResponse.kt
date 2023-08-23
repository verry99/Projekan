package com.test.test.data.remote.dto.notification


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
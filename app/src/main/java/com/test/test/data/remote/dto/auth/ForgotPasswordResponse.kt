package com.test.test.data.remote.dto.auth


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
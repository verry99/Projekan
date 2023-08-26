package com.test.test.data.remote.dto.profile.update_email


import com.google.gson.annotations.SerializedName

data class UpdateEmailResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("expires_in")
    val expiresIn: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("user")
    val user: User?
)
package com.test.test.data.remote.dto.auth


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int?,
    @SerializedName("status")
    val status: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user")
    val user: UserDTO
)
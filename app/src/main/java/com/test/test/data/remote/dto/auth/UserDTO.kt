package com.test.test.data.remote.dto.auth


import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("role")
    val role: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("isVerified")
    val isVerified: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("profile")
    val profile: ProfileDTO?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
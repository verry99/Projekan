package com.test.test.data.remote.dto.profile.update_email


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isVerified")
    val isVerified: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("profile")
    val profile: Profile?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
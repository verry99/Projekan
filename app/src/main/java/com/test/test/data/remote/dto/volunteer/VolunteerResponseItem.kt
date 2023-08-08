package com.test.test.data.remote.dto.volunteer


import com.google.gson.annotations.SerializedName

data class VolunteerResponseItem(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isVerified")
    val isVerified: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("profile")
    val profile: Profile?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("suporter_count")
    val supporterCount: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
package com.test.test.data.remote.dto.volunteer.detail_volunteer


import com.google.gson.annotations.SerializedName

data class Volunteer(
    @SerializedName("area")
    val area: List<Any?>?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("gender")
    val gender: List<Any?>?,
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
    @SerializedName("suporter")
    val suporter: List<Any?>?,
    @SerializedName("suporter_count")
    val suporterCount: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
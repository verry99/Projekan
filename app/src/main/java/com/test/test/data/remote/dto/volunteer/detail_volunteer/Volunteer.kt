package com.test.test.data.remote.dto.volunteer.detail_volunteer


import com.google.gson.annotations.SerializedName

data class Volunteer(
    @SerializedName("email")
    val email: String?,
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
    @SerializedName("area")
    val area: List<Area?>?,
    @SerializedName("suporter")
    val supporter: List<Supporter?>?,
    @SerializedName("suporter_count")
    val supporterCount: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
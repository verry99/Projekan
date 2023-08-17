package com.test.test.data.remote.dto.volunteer.request_upgrade.detail


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("profile")
    val profile: Profile?,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("user_id")
    val userId: String?
)
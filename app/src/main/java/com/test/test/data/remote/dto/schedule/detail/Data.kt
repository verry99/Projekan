package com.test.test.data.remote.dto.schedule.detail


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
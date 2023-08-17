package com.test.test.data.remote.dto.real_counts.add


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("rivals")
    val rivals: String?,
    @SerializedName("subdistrict")
    val subdistrict: String?,
    @SerializedName("tps")
    val tps: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("vilage")
    val vilage: String?
)
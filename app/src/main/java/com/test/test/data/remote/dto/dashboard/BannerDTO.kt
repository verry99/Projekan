package com.test.test.data.remote.dto.dashboard


import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Banner

data class BannerDTO(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("img_url")
    val imgUrl: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

fun BannerDTO.toModel(): Banner {
    return Banner(id!!, imgUrl!!, title!!)
}
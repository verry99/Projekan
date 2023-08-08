package com.test.test.data.remote.dto.dashboard


import com.google.gson.annotations.SerializedName
import com.test.test.domain.models.Post

data class BeritaDTO(
    @SerializedName("author")
    val author: Author?,
    @SerializedName("banner")
    val banner: String?,
    @SerializedName("banner_url")
    val bannerUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("human_readable_date")
    val humanReadableDate: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("title")
    val title: String?
)

fun BeritaDTO.toModel(): Post {
    return Post(id!!, title!!, slug!!, "", humanReadableDate!!, bannerUrl!!)
}
package com.test.test.data.remote.dto.post

import com.google.gson.annotations.SerializedName

data class PostResponseItem(
    @SerializedName("author")
    val author: Any?,
    @SerializedName("banner")
    val banner: String?,
    @SerializedName("banner_url")
    val bannerUrl: String?,
    @SerializedName("category")
    val category: Any?,
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
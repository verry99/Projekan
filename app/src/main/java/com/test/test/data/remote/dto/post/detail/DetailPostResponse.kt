package com.test.test.data.remote.dto.post.detail


import com.google.gson.annotations.SerializedName

data class DetailPostResponse(
    @SerializedName("banner")
    val banner: String?,
    @SerializedName("banner_url")
    val bannerUrl: String?,
    @SerializedName("blog_author_id")
    val blogAuthorId: String?,
    @SerializedName("blog_category_id")
    val blogCategoryId: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("excerpt")
    val excerpt: String?,
    @SerializedName("human_readable_date")
    val humanReadableDate: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
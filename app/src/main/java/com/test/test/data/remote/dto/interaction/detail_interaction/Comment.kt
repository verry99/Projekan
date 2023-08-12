package com.test.test.data.remote.dto.interaction.detail_interaction


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("data")
    val `data`: List<InteractionCommentResponseItem>?,
    @SerializedName("first_page_url")
    val firstPageUrl: String?,
    @SerializedName("from")
    val from: Any?,
    @SerializedName("last_page")
    val lastPage: Int?,
    @SerializedName("last_page_url")
    val lastPageUrl: String?,
    @SerializedName("links")
    val links: List<Link?>?,
    @SerializedName("next_page_url")
    val nextPageUrl: Any?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?,
    @SerializedName("to")
    val to: Any?,
    @SerializedName("total")
    val total: Int?
)
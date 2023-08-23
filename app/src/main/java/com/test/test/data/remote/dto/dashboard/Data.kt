package com.test.test.data.remote.dto.dashboard


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banners")
    val banners: List<BannerDTO>?,
    @SerializedName("posts")
    val posts: Posts?,
    @SerializedName("notifications")
    val notification: Int?,
)
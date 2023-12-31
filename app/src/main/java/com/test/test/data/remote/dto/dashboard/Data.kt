package com.test.test.data.remote.dto.dashboard

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banners")
    val banners: List<BannerDTO>?,
    @SerializedName("berita")
    val berita: List<BeritaDTO>?,
    @SerializedName("opini")
    val opini: List<OpiniDTO>?,
    @SerializedName("notifications")
val notification: Int?,
)
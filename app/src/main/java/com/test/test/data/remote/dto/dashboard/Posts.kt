package com.test.test.data.remote.dto.dashboard


import com.google.gson.annotations.SerializedName

data class Posts(
    @SerializedName("berita")
    val berita: List<BeritaDTO>?,
    @SerializedName("opini")
    val opini: List<OpiniDTO>?
)
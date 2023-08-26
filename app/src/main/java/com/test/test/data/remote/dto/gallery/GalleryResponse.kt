package com.test.test.data.remote.dto.gallery


import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
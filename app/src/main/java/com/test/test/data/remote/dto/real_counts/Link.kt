package com.test.test.data.remote.dto.real_counts


import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("url")
    val url: String?
)
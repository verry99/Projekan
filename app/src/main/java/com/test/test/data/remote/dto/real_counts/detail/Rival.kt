package com.test.test.data.remote.dto.real_counts.detail


import com.google.gson.annotations.SerializedName

data class Rival(
    @SerializedName("name")
    val name: String?,
    @SerializedName("suara")
    val suara: Int?
)
package com.test.test.data.remote.dto.real_counts


import com.google.gson.annotations.SerializedName

data class Rivals(
    @SerializedName("antok s. kom")
    val antokSKom: Int?,
    @SerializedName("tejo")
    val tejo: Int?
)
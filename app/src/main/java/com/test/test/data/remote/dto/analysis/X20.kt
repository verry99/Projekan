package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName

data class X20(
    @SerializedName("L")
    val l: String?,
    @SerializedName("P")
    val p: Int?
)
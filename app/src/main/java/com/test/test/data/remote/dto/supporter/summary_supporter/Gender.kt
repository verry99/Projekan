package com.test.test.data.remote.dto.supporter.summary_supporter


import com.google.gson.annotations.SerializedName

data class Gender(
    @SerializedName("L")
    val l: Int?,
    @SerializedName("P")
    val p: Int?
)
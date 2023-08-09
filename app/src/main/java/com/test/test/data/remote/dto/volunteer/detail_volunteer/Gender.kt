package com.test.test.data.remote.dto.volunteer.detail_volunteer


import com.google.gson.annotations.SerializedName

data class Gender(
    @SerializedName("P")
    val p: Int?,
    @SerializedName("L")
    val l: Int?
)
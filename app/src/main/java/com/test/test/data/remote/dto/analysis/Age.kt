package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName

data class Age(
    @SerializedName("<20")
    val x20: X20,
    @SerializedName("20-29")
    val x2029: X2029,
    @SerializedName("30-39")
    val x3039: X3039,
    @SerializedName("40-49")
    val x4049: X3039,
    @SerializedName("50-59")
    val x5059: X3039,
    @SerializedName("60-69")
    val x6069: X3039,
    @SerializedName("70-79")
    val x7079: X3039,
    @SerializedName(">80")
    val x80: X3039
)
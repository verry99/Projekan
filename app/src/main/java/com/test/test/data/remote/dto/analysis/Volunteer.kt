package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName

data class Volunteer(
    @SerializedName("x_values")
    val xValues: List<String>,
    @SerializedName("y_values")
    val yValues: List<Int>
)
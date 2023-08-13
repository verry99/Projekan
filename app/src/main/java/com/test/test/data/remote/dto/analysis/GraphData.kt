package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName

data class GraphData(
    @SerializedName("x")
    val x: List<String?>?,
    @SerializedName("y")
    val y: List<Int?>?
)
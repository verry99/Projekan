package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("gender")
    val gender: Gender?,
    @SerializedName("graph_data")
    val graphData: GraphData?,
    @SerializedName("presentase")
    val presentase: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("totalPertumbuhan")
    val totalPertumbuhan: Int?
)
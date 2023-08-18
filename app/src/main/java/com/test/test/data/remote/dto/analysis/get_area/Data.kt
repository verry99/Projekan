package com.test.test.data.remote.dto.analysis.get_area


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("province")
    val province: List<String?>?,
    @SerializedName("regency")
    val regency: List<String?>?,
    @SerializedName("subdistrict")
    val subdistrict: List<String?>?,
    @SerializedName("tps")
    val tps: List<String?>?,
    @SerializedName("village")
    val village: List<String?>?
)
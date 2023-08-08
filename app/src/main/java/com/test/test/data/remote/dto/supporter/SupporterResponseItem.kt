package com.test.test.data.remote.dto.supporter


import com.google.gson.annotations.SerializedName

data class SupporterResponseItem(
    @SerializedName("added_by")
    val addedBy: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("regency")
    val regency: String?,
    @SerializedName("subdistrict")
    val subdistrict: String?,
    @SerializedName("village")
    val village: String?
)
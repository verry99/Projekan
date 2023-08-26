package com.test.test.domain.models

import com.google.gson.annotations.SerializedName

data class Rival(
    @SerializedName("name")
    val name: String,
    @SerializedName("suara")
    val voice: Int,
)

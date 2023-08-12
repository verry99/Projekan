package com.test.test.data.remote.dto.interaction.detail_interaction


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
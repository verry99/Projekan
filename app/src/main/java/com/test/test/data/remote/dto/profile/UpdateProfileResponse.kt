package com.test.test.data.remote.dto.profile


import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("message")
    val message: String
)
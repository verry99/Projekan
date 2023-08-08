package com.test.test.data.remote.dto.dashboard


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: Any?,
    @SerializedName("photo_url")
    val photoUrl: String?
)
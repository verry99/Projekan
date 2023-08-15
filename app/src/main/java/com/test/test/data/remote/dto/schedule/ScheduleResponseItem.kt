package com.test.test.data.remote.dto.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleResponseItem(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("title")
    val title: String?
)
package com.test.test.data.remote.dto.analysis


import com.google.gson.annotations.SerializedName
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Area

data class Data(
    @SerializedName("age")
    val age: Age?,
    @SerializedName("area")
    val area: List<Area?>?,
    @SerializedName("volunteerArea")
    val areaVolunteer: List<Area?>?,
    @SerializedName("genderSuppoter")
    val genderSupporter: GenderSuppoter?,
    @SerializedName("pertumbuhanSupporter")
    val growthSupporter: Int?,
    @SerializedName("presentaseSuppoter")
    val percentageSupporter: Int?,
    @SerializedName("supporter")
    val supporter: Supporter?,
    @SerializedName("totalSuporter")
    val totalSupporter: Int?,
    @SerializedName("totalVolunteer")
    val totalVolunteer: Int?,
    @SerializedName("volunteer")
    val volunteer: Volunteer?
)
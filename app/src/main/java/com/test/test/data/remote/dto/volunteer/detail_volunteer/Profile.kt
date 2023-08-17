package com.test.test.data.remote.dto.volunteer.detail_volunteer


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Profile(
    @SerializedName("address")
    val address: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("date_of_birth")
    val dateOfBirth: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isCompleted")
    val isCompleted: String?,
    @SerializedName("marial_state")
    val marialState: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nik")
    val nik: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("regency")
    val regency: String?,
    @SerializedName("religion")
    val religion: String?,
    @SerializedName("rt")
    val rt: String?,
    @SerializedName("rw")
    val rw: String?,
    @SerializedName("subdistrict")
    val subdistrict: String?,
    @SerializedName("tps")
    val tps: String?,
    @SerializedName("age")
    val age: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("village")
    val village: String?
)
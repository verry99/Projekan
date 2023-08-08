package com.test.test.data.remote.api

import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.data.remote.dto.detail.DetailPostResponse
import com.test.test.data.remote.dto.post.PostResponse
import com.test.test.data.remote.dto.volunteer.VolunteerResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DashboardService {

    @GET("home")
    suspend fun getDashboard(
        @Header("Authorization") token: String
    ): DashboardResponse

    @GET("post")
    suspend fun getAllPost(
        @Header("Authorization") token: String,
        @Query("category") category: String
    ): PostResponse

    @GET("post/{slug}")
    suspend fun getDetailPost(
        @Header("Authorization") token: String,
        @Path("slug") slug: String
    ): DetailPostResponse

    @GET("volunteer")
    suspend fun getAllVolunteer(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): VolunteerResponse

    @GET("search")
    suspend fun getVolunteerByName(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String,
        @Query("role") role: String
    ): VolunteerResponse

    @Multipart
    @POST("user-profile")
    suspend fun postProfile(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part nik: Int,
        @Part name: String,
        @Part phone: String,
        @Part birthPlace: String,
        @Part birthDate: String,
        @Part gender: Char,
        @Part address: String,
        @Part rt: Int,
        @Part rw: Int,
        @Part tps: Int,
        @Part province: String,
        @Part regency: String,
        @Part subDistrict: String,
        @Part village: String,
        @Part religion: String,
        @Part maritalStatus: String
    )
}
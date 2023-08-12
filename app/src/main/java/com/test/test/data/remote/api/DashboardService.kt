package com.test.test.data.remote.api

import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.data.remote.dto.detail.DetailPostResponse
import com.test.test.data.remote.dto.interaction.InteractionResponse
import com.test.test.data.remote.dto.interaction.add_interaction.AddInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.DetailInteractionResponse
import com.test.test.data.remote.dto.post.PostResponse
import com.test.test.data.remote.dto.profile.ProfileResponse
import com.test.test.data.remote.dto.profile.UpdateProfileResponse
import com.test.test.data.remote.dto.supporter.SupporterResponse
import com.test.test.data.remote.dto.supporter.detail_supporter.DetailSupporterResponse
import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse
import com.test.test.data.remote.dto.volunteer.VolunteerResponse
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Query("category") category: String,
        @Query("page") page: Int
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

    @GET("volunteer")
    suspend fun getAllVolunteerSummary(
        @Header("Authorization") token: String
    ): VolunteerSummaryResponse

    @GET("search")
    suspend fun searchVolunteer(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String,
        @Query("role") role: String
    ): VolunteerResponse

    @GET("volunteer/{id}")
    suspend fun getVolunteer(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailVolunteerResponse

    @Multipart
    @POST("volunteer")
    suspend fun addVolunteer(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part?,
        @Part("nik") nik: RequestBody,
        @Part("name") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("place_of_birth") birthPlace: RequestBody,
        @Part("date_of_birth") birthDate: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("address") address: RequestBody,
        @Part("rt") rt: RequestBody,
        @Part("rw") rw: RequestBody,
        @Part("tps") tps: RequestBody,
        @Part("province") province: RequestBody,
        @Part("regency") regency: RequestBody,
        @Part("subdistrict") subDistrict: RequestBody,
        @Part("village") village: RequestBody,
        @Part("religion") religion: RequestBody,
        @Part("marial_state") maritalStatus: RequestBody
    ): AddVolunteerResponse

    @GET("suporter")
    suspend fun getAllSupporter(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): SupporterResponse

    @GET("suporter")
    suspend fun getSupporterSummary(
        @Header("Authorization") token: String
    ): SupporterSummaryResponse

    @GET("search")
    suspend fun searchSupporter(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String,
        @Query("role") role: String
    ): SupporterResponse

    @GET("suporter/{id}")
    suspend fun getSupporter(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailSupporterResponse

    @Multipart
    @POST("suporter")
    suspend fun addSupporter(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part?,
        @Part("nik") nik: RequestBody,
        @Part("name") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("place_of_birth") birthPlace: RequestBody,
        @Part("date_of_birth") birthDate: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("address") address: RequestBody,
        @Part("rt") rt: RequestBody,
        @Part("rw") rw: RequestBody,
        @Part("tps") tps: RequestBody,
        @Part("province") province: RequestBody,
        @Part("regency") regency: RequestBody,
        @Part("subdistrict") subDistrict: RequestBody,
        @Part("village") village: RequestBody,
        @Part("religion") religion: RequestBody,
        @Part("marial_state") maritalStatus: RequestBody
    ): AddVolunteerResponse

    @GET("interaction")
    suspend fun getAllInteraction(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): InteractionResponse

    @GET("interaction/{id}")
    suspend fun getInteraction(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailInteractionResponse

    @GET("interaction/{id}")
    suspend fun getInteractionComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("page") page: Int
    ): DetailInteractionResponse

    @Multipart
    @POST("interaction")
    suspend fun addInteraction(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
    ): AddInteractionResponse

    @GET("user-profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @Multipart
    @POST("user-profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part?,
        @Part("nik") nik: RequestBody,
        @Part("name") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("place_of_birth") birthPlace: RequestBody,
        @Part("date_of_birth") birthDate: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("address") address: RequestBody,
        @Part("rt") rt: RequestBody,
        @Part("rw") rw: RequestBody,
        @Part("tps") tps: RequestBody,
        @Part("province") province: RequestBody,
        @Part("regency") regency: RequestBody,
        @Part("subdistrict") subDistrict: RequestBody,
        @Part("village") village: RequestBody,
        @Part("religion") religion: RequestBody,
        @Part("marial_state") maritalStatus: RequestBody
    ): UpdateProfileResponse
}
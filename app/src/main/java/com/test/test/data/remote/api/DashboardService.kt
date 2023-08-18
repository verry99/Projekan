package com.test.test.data.remote.api

import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse
import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.data.remote.dto.interaction.InteractionResponse
import com.test.test.data.remote.dto.interaction.add_interaction.AddInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.DetailInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.add_comment.AddCommentResponse
import com.test.test.data.remote.dto.post.PostResponse
import com.test.test.data.remote.dto.post.detail.DetailPostResponse
import com.test.test.data.remote.dto.profile.ProfileResponse
import com.test.test.data.remote.dto.profile.UpdateProfileResponse
import com.test.test.data.remote.dto.profile.update_password.UpdatePasswordResponse
import com.test.test.data.remote.dto.profile.update_phone.UpdatePhoneResponse
import com.test.test.data.remote.dto.real_counts.RealCountsResponse
import com.test.test.data.remote.dto.real_counts.add.AddRealCountResponse
import com.test.test.data.remote.dto.real_counts.detail.DetailRealCountResponse
import com.test.test.data.remote.dto.schedule.ScheduleResponse
import com.test.test.data.remote.dto.schedule.detail.DetailScheduleResponse
import com.test.test.data.remote.dto.supporter.SupporterResponse
import com.test.test.data.remote.dto.supporter.detail_supporter.DetailSupporterResponse
import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse
import com.test.test.data.remote.dto.user.request_upgrade_volunteer.RequestUpgradeVolunteerStatusResponse
import com.test.test.data.remote.dto.volunteer.VolunteerResponse
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.RequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.accept_reject.UpdateRequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.detail.DetailRequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
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
    suspend fun getDetailVolunteer(
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

    @Multipart
    @POST("volunteer/{id}")
    suspend fun updateVolunteer(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
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

    @GET("request-upgrade")
    suspend fun getAllRequestUpgradeVolunteer(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): RequestUpgradeVolunteerResponse

    @GET("request-upgrade/{id}")
    suspend fun getDetailRequestUpgradeVolunteer(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailRequestUpgradeVolunteerResponse

    @GET("request-upgrade")
    suspend fun getRequestUpgradeVolunteer(
        @Header("Authorization") token: String
    ): RequestUpgradeVolunteerStatusResponse

    @FormUrlEncoded
    @POST("request-upgrade")
    suspend fun requestUpgradeVolunteer(
        @Header("Authorization") token: String,
        @Field("role") role: String,
        @Field("reason") reason: String
    ): RequestUpgradeVolunteerStatusResponse

    @FormUrlEncoded
    @PUT("request-upgrade/{id}")
    suspend fun updateRequestUpgradeVolunteer(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("status") status: String,
    ): UpdateRequestUpgradeVolunteerResponse


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

    @FormUrlEncoded
    @POST("interaction/{id}/comment")
    suspend fun addInteractionComment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("body") body: String,
    ): AddCommentResponse

    @GET("schedule")
    suspend fun getAllSchedule(
        @Header("Authorization") token: String,
        @Query("filter") filter: String,
        @Query("page") page: Int,
    ): ScheduleResponse

    @GET("schedule/{id}")
    suspend fun getDetailSchedule(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailScheduleResponse

    @GET("real-counts")
    suspend fun getAllRealCounts(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
    ): RealCountsResponse

    @GET("real-counts/{id}")
    suspend fun getDetailRealCount(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailRealCountResponse

    @Multipart
    @POST("real-counts")
    suspend fun addRealCount(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("tps") tps: RequestBody,
        @Part("count") count: RequestBody,
        @Part("subdistrict") subDistrict: RequestBody,
        @Part("vilage") village: RequestBody,
        @Part("name") name: RequestBody,
        @Part("suara") voice: RequestBody,
    ): AddRealCountResponse

    @GET("analyst")
    suspend fun getAnalysis(
        @Header("Authorization") token: String
    ): AnalysisResponse

    @GET("analyst/get-area")
    suspend fun getAnalysisArea(
        @Header("Authorization") token: String
    ): AnalysisGetAreaResponse

    @GET("analyst")
    suspend fun getAnalysisDataByArea(
        @Header("Authorization") token: String,
        @Query("area") area: String,
        @Query("type") type: String
    ): AnalysisDataByAreaResponse

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

    @FormUrlEncoded
    @POST("password-update")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Field("old_password") oldPassword: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
    ) : UpdatePasswordResponse

    @FormUrlEncoded
    @POST("phone-update")
    suspend fun updatePhone(
        @Header("Authorization") token: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
    ): UpdatePhoneResponse
}
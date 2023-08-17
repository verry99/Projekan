package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.RequestUpgradeVolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.request_upgrade.accept_reject.UpdateRequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.detail.DetailRequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface VolunteerRepository {

    fun getAllVolunteer(token: String, page: Int): LiveData<PagingData<VolunteerResponseItem>>

    suspend fun getAllVolunteerSummary(token: String): VolunteerSummaryResponse

    fun getVolunteerByName(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<VolunteerResponseItem>>

    suspend fun getDetailVolunteer(token: String, id: Int): DetailVolunteerResponse

    suspend fun addVolunteer(
        token: String,
        photo: MultipartBody.Part?,
        nik: RequestBody,
        name: RequestBody,
        phone: RequestBody,
        email: RequestBody,
        birthPlace: RequestBody,
        birthDate: RequestBody,
        gender: RequestBody,
        address: RequestBody,
        rt: RequestBody,
        rw: RequestBody,
        tps: RequestBody,
        province: RequestBody,
        regency: RequestBody,
        subDistrict: RequestBody,
        village: RequestBody,
        religion: RequestBody,
        maritalStatus: RequestBody
    ): AddVolunteerResponse

    fun getAllRequestUpgradeVolunteer(
        token: String,
        page: Int
    ): LiveData<PagingData<RequestUpgradeVolunteerResponseItem>>

    suspend fun getDetailRequestUpgradeVolunteer(
        token: String,
        id: Int
    ): DetailRequestUpgradeVolunteerResponse

    suspend fun updateRequestUpgradeVolunteer(
        token: String,
        id: Int,
        status: String
    ): UpdateRequestUpgradeVolunteerResponse
}
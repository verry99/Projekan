package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.data.remote.dto.supporter.detail_supporter.DetailSupporterResponse
import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SupporterRepository {

    fun getALlSupporter(token: String, page: Int): LiveData<PagingData<SupporterResponseItem>>

    suspend fun getSupporterSummary(token: String): SupporterSummaryResponse

    fun getSupporterByName(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<SupporterResponseItem>>

    suspend fun getSupporter(token: String, id: Int): DetailSupporterResponse

    suspend fun addSupporter(
        token: String,
        photo: MultipartBody.Part?,
        nik: RequestBody,
        name: RequestBody,
        phone: RequestBody,
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
        maritalStatus: RequestBody,
    ): AddVolunteerResponse
}
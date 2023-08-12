package com.test.test.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.data.remote.dto.supporter.detail_supporter.DetailSupporterResponse
import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.data.remote.paging_source.SearchSupporterPagingSource
import com.test.test.data.remote.paging_source.SupporterPagingSource
import com.test.test.domain.repository.SupporterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SupporterRepositoryImpl(
    private val dashboardService: DashboardService
) : SupporterRepository {

    override fun getALlSupporter(
        token: String,
        page: Int
    ): LiveData<PagingData<SupporterResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = page
            ),
            pagingSourceFactory = {
                SupporterPagingSource(dashboardService, token)
            }
        ).liveData
    }

    override suspend fun getSupporterSummary(token: String): SupporterSummaryResponse {
        Log.e("#suprepimpl", "${dashboardService.getSupporterSummary(token)}")
        return dashboardService.getSupporterSummary(token)
    }

    override fun getSupporterByName(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<SupporterResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchSupporterPagingSource(dashboardService, token, keyword, role)
            }
        ).liveData
    }

    override suspend fun getSupporter(
        token: String,
        id: Int
    ): DetailSupporterResponse {
        return dashboardService.getSupporter(token, id)
    }

    override suspend fun addSupporter(
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
    ): AddVolunteerResponse {

        return dashboardService.addSupporter(
            token,
            photo,
            nik,
            name,
            phone,
            birthPlace,
            birthDate,
            gender,
            address,
            rt,
            rw,
            tps,
            province,
            regency,
            subDistrict,
            village,
            religion,
            maritalStatus
        )
    }
}
package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.RequestUpgradeVolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.request_upgrade.accept_reject.UpdateRequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.request_upgrade.detail.DetailRequestUpgradeVolunteerResponse
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
import com.test.test.data.remote.paging_source.RequestUpgradeVolunteerPagingSource
import com.test.test.data.remote.paging_source.SearchVolunteerPagingSource
import com.test.test.data.remote.paging_source.VolunteerPagingSource
import com.test.test.domain.repository.VolunteerRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class VolunteerRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : VolunteerRepository {

    override fun getAllVolunteer(
        token: String,
        page: Int
    ): LiveData<PagingData<VolunteerResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = page
            ),
            pagingSourceFactory = {
                VolunteerPagingSource(dashboardService, token)
            }
        ).liveData
    }

    override suspend fun getAllVolunteerSummary(token: String): VolunteerSummaryResponse {
        return dashboardService.getAllVolunteerSummary(token)
    }

    override fun getVolunteerByName(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<VolunteerResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchVolunteerPagingSource(dashboardService, token, keyword, role)
            }
        ).liveData
    }

    override suspend fun getDetailVolunteer(token: String, id: Int): DetailVolunteerResponse {
        return dashboardService.getDetailVolunteer(token, id)
    }

    override suspend fun addVolunteer(
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
    ): AddVolunteerResponse {

        return dashboardService.addVolunteer(
            token,
            photo,
            nik,
            name,
            phone,
            email,
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

    override suspend fun updateVolunteer(
        token: String,
        id: Int,
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
    ): AddVolunteerResponse {

        return dashboardService.updateVolunteer(
            token,
            id,
            photo,
            nik,
            name,
            phone,
            email,
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
            maritalStatus,
            "PUT"
        )
    }

    override fun getAllRequestUpgradeVolunteer(
        token: String,
        page: Int
    ): LiveData<PagingData<RequestUpgradeVolunteerResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = page
            ),
            pagingSourceFactory = {
                RequestUpgradeVolunteerPagingSource(dashboardService, token)
            }
        ).liveData
    }

    override suspend fun getDetailRequestUpgradeVolunteer(
        token: String,
        id: Int
    ): DetailRequestUpgradeVolunteerResponse {
        return dashboardService.getDetailRequestUpgradeVolunteer(token, id)
    }

    override suspend fun updateRequestUpgradeVolunteer(
        token: String,
        id: Int,
        status: String
    ): UpdateRequestUpgradeVolunteerResponse {
        return dashboardService.updateRequestUpgradeVolunteer(token, id, status)
    }
}
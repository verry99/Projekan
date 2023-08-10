package com.test.test.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse
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

    override suspend fun getVolunteer(token: String, id: Int): DetailVolunteerResponse {
        return dashboardService.getVolunteer(token, id)
    }

    override suspend fun addVolunteer(
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
        maritalStatus: RequestBody
    ): AddVolunteerResponse {

        Log.e(
            "#volrepimpl",
            "$token $photo $nik $name $phone $birthPlace $birthDate $gender $address $rt $rw $tps $province $regency $subDistrict $village $religion $maritalStatus"
        )

        return dashboardService.addVolunteer(
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
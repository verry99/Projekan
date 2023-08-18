package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.real_counts.RealCountResponseItem
import com.test.test.data.remote.dto.real_counts.RealCountsResponse
import com.test.test.data.remote.dto.real_counts.add.AddRealCountResponse
import com.test.test.data.remote.dto.real_counts.detail.DetailRealCountResponse
import com.test.test.data.remote.paging_source.RealCountPagingSource
import com.test.test.domain.repository.RealCountRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RealCountRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : RealCountRepository {

    override fun getAllRealCount(
        token: String,
        page: Int
    ): LiveData<PagingData<RealCountResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = page
            ),
            pagingSourceFactory = {
                RealCountPagingSource(dashboardService, token)
            }
        ).liveData
    }

    override suspend fun getAllRealCountSummary(token: String): RealCountsResponse {
        return dashboardService.getAllRealCounts(token, 1)
    }

    override suspend fun addRealCount(
        token: String,
        image: MultipartBody.Part,
        tps: RequestBody,
        count: RequestBody,
        subDistrict: RequestBody,
        village: RequestBody,
        name: RequestBody,
        voice: RequestBody
    ): AddRealCountResponse {
        return dashboardService.addRealCount(
            token,
            image,
            tps,
            count,
            subDistrict,
            village,
            name,
            voice
        )
    }

    override suspend fun getDetailRealCount(token: String, id: Int): DetailRealCountResponse {
        return dashboardService.getDetailRealCount(token, id)
    }

}
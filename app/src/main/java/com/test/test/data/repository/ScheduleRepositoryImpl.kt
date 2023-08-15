package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.schedule.ScheduleResponseItem
import com.test.test.data.remote.dto.schedule.detail.DetailScheduleResponse
import com.test.test.data.remote.paging_source.SchedulePagingSource
import com.test.test.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : ScheduleRepository {
    override fun getAllSchedule(
        token: String,
        filter: String
    ): LiveData<PagingData<ScheduleResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SchedulePagingSource(dashboardService, token, filter)
            }
        ).liveData
    }

    override suspend fun getDetailSchedule(token: String, id: Int): DetailScheduleResponse {
        return dashboardService.getDetailSchedule(token, id)
    }
}
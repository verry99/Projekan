package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.paging_source.SearchVolunteerPagingSource
import com.test.test.data.remote.paging_source.VolunteerPagingSource
import com.test.test.domain.repository.VolunteerRepository
import javax.inject.Inject

class VolunteerRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : VolunteerRepository {

    override fun getVolunteer(
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
}
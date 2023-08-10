package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.data.remote.paging_source.SupporterPagingSource
import com.test.test.domain.repository.SupporterRepository

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
}
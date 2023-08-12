package com.test.test.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.supporter.SupporterResponseItem

class SearchSupporterPagingSource(
    private val dashboardService: DashboardService,
    private val token: String,
    private val keyword: String,
    private val role: String
) : PagingSource<Int, SupporterResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, SupporterResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SupporterResponseItem> {
        return try {
            val responseData =
                dashboardService.searchSupporter(token, keyword, role).data!!.supporter!!
            val pageNumber = params.key ?: 0
            val pageSize = params.loadSize
            val startIndex = pageNumber * pageSize
            LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = if (startIndex + pageSize < responseData.size) pageNumber + 1 else null
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
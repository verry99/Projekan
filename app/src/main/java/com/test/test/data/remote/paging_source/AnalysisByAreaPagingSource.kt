package com.test.test.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponseItem

class AnalysisByAreaPagingSource(
    private val dashboardService: DashboardService,
    private val token: String,
    private val area: String,
    private val type: String,
    private val role: String,
) : PagingSource<Int, AnalysisDataByAreaResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, AnalysisDataByAreaResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnalysisDataByAreaResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData =
                dashboardService.getAnalysisDataByArea(
                    token,
                    area,
                    type,
                    role,
                    position
                ).data!!.responseItem!!

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
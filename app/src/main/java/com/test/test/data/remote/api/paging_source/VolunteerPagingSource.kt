package com.test.test.data.remote.api.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem

class VolunteerPagingSource(
    private val dashboardService: DashboardService,
    private val token: String
) : PagingSource<Int, VolunteerResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, VolunteerResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VolunteerResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = dashboardService.getAllVolunteer(token, position).data!!.volunteers!!
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
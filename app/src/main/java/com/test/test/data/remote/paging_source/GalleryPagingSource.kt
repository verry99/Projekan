package com.test.test.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.gallery.GalleryResponseItem

class GalleryPagingSource(
    private val dashboardService: DashboardService,
    private val token: String,
) : PagingSource<Int, GalleryResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, GalleryResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, GalleryResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData =
                dashboardService.getAllGallery(token, position).data!!.gallery!!
            PagingSource.LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return PagingSource.LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
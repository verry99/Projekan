package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.post.detail.DetailPostResponse
import com.test.test.data.remote.dto.post.PostResponseItem
import com.test.test.data.remote.paging_source.PostPagingSource
import com.test.test.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : PostRepository {

    override fun getAllNews(
        token: String
    ): LiveData<PagingData<PostResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                PostPagingSource(dashboardService, token, "berita")
            }
        ).liveData
    }

    override fun getAllOpinion(
        token: String
    ): LiveData<PagingData<PostResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                PostPagingSource(dashboardService, token, "opini")
            }
        ).liveData
    }

    override suspend fun getDetailPost(
        token: String,
        slug: String
    ): DetailPostResponse {
        return dashboardService.getDetailPost(token, slug)
    }
}
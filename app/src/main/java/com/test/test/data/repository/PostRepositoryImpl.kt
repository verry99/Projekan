package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.post.PostResponse
import com.test.test.data.remote.dto.detail.DetailPostResponse
import com.test.test.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : PostRepository {

    override suspend fun getAllNews(token: String): PostResponse {
        return dashboardService.getAllPost(
            token,
            "berita"
        )
    }

    override suspend fun getAllOpinion(token: String): PostResponse {
        return dashboardService.getAllPost(
            token, "opini"
        )
    }

    override suspend fun getDetailPost(token: String, slug: String): DetailPostResponse {
        return dashboardService.getDetailPost(token, slug)
    }
}
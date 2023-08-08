package com.test.test.domain.repository

import com.test.test.data.remote.dto.post.PostResponse
import com.test.test.data.remote.dto.detail.DetailPostResponse

interface PostRepository {
    suspend fun getAllNews(token: String): PostResponse
    suspend fun getAllOpinion(token: String): PostResponse
    suspend fun getDetailPost(token: String, slug: String): DetailPostResponse
}
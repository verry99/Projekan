package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.post.detail.DetailPostResponse
import com.test.test.data.remote.dto.post.PostResponseItem

interface PostRepository {
    fun getAllNews(token: String): LiveData<PagingData<PostResponseItem>>
    fun getAllOpinion(token: String): LiveData<PagingData<PostResponseItem>>
    suspend fun getDetailPost(token: String, slug: String): DetailPostResponse
}
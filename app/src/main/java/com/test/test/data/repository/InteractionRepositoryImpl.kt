package com.test.test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.interaction.InteractionResponseItem
import com.test.test.data.remote.dto.interaction.add_interaction.AddInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.DetailInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.InteractionCommentResponseItem
import com.test.test.data.remote.paging_source.InteractionCommentPagingSource
import com.test.test.data.remote.paging_source.InteractionPagingSource
import com.test.test.domain.repository.InteractionRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class InteractionRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : InteractionRepository {
    override fun getAllInteraction(
        token: String,
        page: Int
    ): LiveData<PagingData<InteractionResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = page
            ),
            pagingSourceFactory = {
                InteractionPagingSource(dashboardService, token)
            }
        ).liveData
    }

    override suspend fun getInteraction(token: String, id: Int): DetailInteractionResponse {
        return dashboardService.getInteraction(token, id)
    }

    override suspend fun addInteraction(
        token: String,
        photo: MultipartBody.Part,
        title: RequestBody,
        description: RequestBody
    ): AddInteractionResponse {
        return dashboardService.addInteraction(token, photo, title, description)
    }

    override fun getInteractionComment(
        token: String,
        page: Int,
        id: Int,
    ): LiveData<PagingData<InteractionCommentResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = page
            ),
            pagingSourceFactory = {
                InteractionCommentPagingSource(dashboardService, token, id)
            }
        ).liveData
    }

}
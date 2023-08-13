package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.interaction.InteractionResponseItem
import com.test.test.data.remote.dto.interaction.add_interaction.AddInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.DetailInteractionResponse
import com.test.test.data.remote.dto.interaction.detail_interaction.InteractionCommentResponseItem
import com.test.test.data.remote.dto.interaction.detail_interaction.add_comment.AddCommentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface InteractionRepository {
    fun getAllInteraction(token: String, page: Int): LiveData<PagingData<InteractionResponseItem>>

    suspend fun getInteraction(token: String, id: Int): DetailInteractionResponse

    suspend fun addInteraction(
        token: String,
        photo: MultipartBody.Part,
        title: RequestBody,
        description: RequestBody,
    ): AddInteractionResponse

    fun getInteractionComment(
        token: String,
        page: Int,
        id: Int
    ): LiveData<PagingData<InteractionCommentResponseItem>>

    suspend fun addComment(
        token: String,
        id: Int,
        body: String
    ): AddCommentResponse
}
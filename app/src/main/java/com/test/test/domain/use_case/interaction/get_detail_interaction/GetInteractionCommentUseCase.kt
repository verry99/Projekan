package com.test.test.domain.use_case.interaction.get_detail_interaction

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.interaction.detail_interaction.InteractionCommentResponseItem
import com.test.test.domain.repository.InteractionRepository
import javax.inject.Inject

class GetInteractionCommentUseCase @Inject constructor(
    private val interactionRepository: InteractionRepository
) {
    operator fun invoke(
        token: String,
        id: Int,
        page: Int
    ): LiveData<PagingData<InteractionCommentResponseItem>> {
        return interactionRepository.getInteractionComment(token, page, id)
    }
}
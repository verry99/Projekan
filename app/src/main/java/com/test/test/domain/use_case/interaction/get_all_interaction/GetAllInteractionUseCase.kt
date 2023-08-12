package com.test.test.domain.use_case.interaction.get_all_interaction

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.interaction.InteractionResponseItem
import com.test.test.domain.repository.InteractionRepository
import javax.inject.Inject

class GetAllInteractionUseCase @Inject constructor(
    private val interactionRepository: InteractionRepository
) {
    operator fun invoke(token: String, page: Int): LiveData<PagingData<InteractionResponseItem>> {
        return interactionRepository.getAllInteraction(token, page)
    }
}
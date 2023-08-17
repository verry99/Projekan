package com.test.test.domain.use_case.real_count

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.interaction.InteractionResponseItem
import com.test.test.data.remote.dto.real_counts.RealCountResponseItem
import com.test.test.domain.repository.InteractionRepository
import com.test.test.domain.repository.RealCountRepository
import javax.inject.Inject

class GetAllRealCountUseCase @Inject constructor(
    private val realCountRepository: RealCountRepository
) {
    operator fun invoke(token: String, page: Int): LiveData<PagingData<RealCountResponseItem>> {
        return realCountRepository.getAllRealCount(token, page)
    }
}
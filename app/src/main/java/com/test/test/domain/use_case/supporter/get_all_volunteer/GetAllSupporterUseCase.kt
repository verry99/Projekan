package com.test.test.domain.use_case.supporter.get_all_volunteer

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.domain.repository.SupporterRepository
import javax.inject.Inject

class GetAllSupporterUseCase @Inject constructor(
    private val supporterRepository: SupporterRepository
) {
    operator fun invoke(token: String, page: Int): LiveData<PagingData<SupporterResponseItem>> {
        return supporterRepository.getALlSupporter(token, page)
    }
}
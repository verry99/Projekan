package com.test.test.domain.use_case.supporter.get_supporter_by_name

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.domain.repository.SupporterRepository
import javax.inject.Inject

class GetSupporterByNameUseCase @Inject constructor(
    private val supporterRepository: SupporterRepository
) {
    operator fun invoke(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<SupporterResponseItem>> {
        return supporterRepository.getSupporterByName(token, keyword, role)
    }
}
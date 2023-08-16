package com.test.test.domain.use_case.volunteer.request_upgrade

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.volunteer.request_upgrade.RequestUpgradeVolunteerResponseItem
import com.test.test.domain.repository.VolunteerRepository
import javax.inject.Inject

class GetAllRequestUpgradeVolunteerUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    operator fun invoke(
        token: String,
        page: Int
    ): LiveData<PagingData<RequestUpgradeVolunteerResponseItem>> {
        return volunteerRepository.getAllRequestUpgradeVolunteer(token, page)
    }
}
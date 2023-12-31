package com.test.test.domain.use_case.volunteer.get_all_volunteer

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.domain.repository.VolunteerRepository
import javax.inject.Inject

class GetAllVolunteerUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    operator fun invoke(token: String, page: Int): LiveData<PagingData<VolunteerResponseItem>> {
        return volunteerRepository.getAllVolunteer(token, page)
    }
}
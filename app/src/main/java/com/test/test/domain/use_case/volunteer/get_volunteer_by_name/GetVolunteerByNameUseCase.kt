package com.test.test.domain.use_case.volunteer.get_volunteer_by_name

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.domain.repository.VolunteerRepository
import javax.inject.Inject

class GetVolunteerByNameUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    operator fun invoke(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<VolunteerResponseItem>> {
        return volunteerRepository.getVolunteerByName(token, keyword, role)
    }
}
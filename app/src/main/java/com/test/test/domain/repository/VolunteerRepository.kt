package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.volunteer.VolunteerResponse
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem

interface VolunteerRepository {

    fun getVolunteer(token: String, page: Int): LiveData<PagingData<VolunteerResponseItem>>

    suspend fun getVolunteerByName(
        token: String,
        keyword: String,
        role: String
    ): VolunteerResponse
}
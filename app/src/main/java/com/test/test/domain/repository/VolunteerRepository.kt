package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.data.remote.dto.volunteer.summary_volunteer.VolunteerSummaryResponse

interface VolunteerRepository {

    fun getAllVolunteer(token: String, page: Int): LiveData<PagingData<VolunteerResponseItem>>

    suspend fun getAllVolunteerSummary(token: String): VolunteerSummaryResponse

    fun getVolunteerByName(
        token: String,
        keyword: String,
        role: String
    ): LiveData<PagingData<VolunteerResponseItem>>

    suspend fun getVolunteer(token: String, id: Int): DetailVolunteerResponse
}
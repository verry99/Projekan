package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.data.remote.dto.supporter.summary_supporter.SupporterSummaryResponse

interface SupporterRepository {

    fun getALlSupporter(token: String, page: Int): LiveData<PagingData<SupporterResponseItem>>

    suspend fun getSupporterSummary(token: String): SupporterSummaryResponse
//
//    fun getVolunteerByName(
//        token: String,
//        keyword: String,
//        role: String
//    ): LiveData<PagingData<VolunteerResponseItem>>
//
//    suspend fun getVolunteer(token: String, id: Int): DetailVolunteerResponse
}
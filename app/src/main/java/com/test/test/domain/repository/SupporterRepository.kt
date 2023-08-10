package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.supporter.SupporterResponseItem

interface SupporterRepository {

    fun getALlSupporter(token: String, page: Int): LiveData<PagingData<SupporterResponseItem>>

//    suspend fun getAllVolunteerSummary(token: String): VolunteerSummaryResponse
//
//    fun getVolunteerByName(
//        token: String,
//        keyword: String,
//        role: String
//    ): LiveData<PagingData<VolunteerResponseItem>>
//
//    suspend fun getVolunteer(token: String, id: Int): DetailVolunteerResponse
}
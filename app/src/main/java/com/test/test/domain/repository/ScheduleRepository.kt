package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.schedule.ScheduleResponseItem
import com.test.test.data.remote.dto.schedule.detail.DetailScheduleResponse

interface ScheduleRepository {

    fun getAllSchedule(token: String, filter: String): LiveData<PagingData<ScheduleResponseItem>>
    suspend fun getDetailSchedule(token: String, id: Int): DetailScheduleResponse
}
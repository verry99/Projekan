package com.test.test.domain.use_case.schedule

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.schedule.ScheduleResponseItem
import com.test.test.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetAllScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    operator fun invoke(token: String): LiveData<PagingData<ScheduleResponseItem>> {
        return scheduleRepository.getAllSchedule(token, "all")
    }
}
package com.test.test.domain.use_case.schedule

import com.test.test.common.Resource
import com.test.test.data.remote.dto.schedule.detail.DetailScheduleResponse
import com.test.test.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(token: String, id: Int): Flow<Resource<DetailScheduleResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = scheduleRepository.getDetailSchedule(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
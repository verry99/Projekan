package com.test.test.domain.use_case.notification

import com.test.test.common.Resource
import com.test.test.data.remote.dto.notification.detail.DetailNotificationResponse
import com.test.test.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailNotificationUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {

    suspend operator fun invoke(
        token: String,
        id: Int
    ): Flow<Resource<DetailNotificationResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = dashboardRepository.getDetailNotification(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
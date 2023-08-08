package com.test.test.domain.use_case.dashboard

import com.test.test.common.Resource
import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDashboardUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<DashboardResponse>> = flow {
        emit(Resource.Loading())
        try {
            val data = dashboardRepository.getDashboard(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
        }
    }
}
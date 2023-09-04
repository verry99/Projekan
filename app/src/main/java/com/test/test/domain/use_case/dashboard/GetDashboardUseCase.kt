package com.test.test.domain.use_case.dashboard

import com.test.test.common.Resource
import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
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
            when (e.code()) {
                in 400..499 -> {
                    if (e.code() == 401) {
                        emit(Resource.Error("expired"))
                    } else {
                        e.response()?.errorBody()?.string()?.let {
                            val errorObj = JSONObject(it)
                            val errorMessage = errorObj.optString("message", "Unknown Error")
                            emit(Resource.Error(errorMessage))
                        }
                    }
                }

                in 500..599 -> {
                    emit(Resource.Error("Server Error."))
                }

                else -> {
                    emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
        }
    }
}
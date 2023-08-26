package com.test.test.domain.use_case.analysis

import com.test.test.common.Resource
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse
import com.test.test.domain.repository.AnalysisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnalysisDataByAreaSummaryUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    suspend operator fun invoke(
        token: String,
        area: String,
        type: String,
        role: String
    ): Flow<Resource<AnalysisDataByAreaResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data =
                    analysisRepository.getAnalysisDataByAreaSummary(token, area, type, role, 1)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                when (e.code()) {
                    in 400..499 -> {
                        e.response()?.errorBody()?.string()?.let {
                            val errorObj = JSONObject(it)
                            val errorMessage = errorObj.optString("message", "Unknown Error")
                            emit(Resource.Error(errorMessage))
                        }
                    }

                    in 500..599 -> {
                        emit(Resource.Error("${e.code()}: Server Error."))
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
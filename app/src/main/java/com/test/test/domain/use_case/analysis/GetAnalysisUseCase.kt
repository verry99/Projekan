package com.test.test.domain.use_case.analysis

import com.test.test.common.Resource
import com.test.test.data.remote.dto.analysis.AnalysisResponse
import com.test.test.domain.repository.AnalysisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnalysisUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<AnalysisResponse>> = flow {
        emit(Resource.Loading())
        try {
            val data = analysisRepository.getAnalysis(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            when (e.code()) {
                in 400..499 -> {
                    emit(Resource.Error("Token expired. Silahkan login kembali!"))
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
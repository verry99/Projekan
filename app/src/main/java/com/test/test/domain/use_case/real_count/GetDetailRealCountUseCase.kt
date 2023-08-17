package com.test.test.domain.use_case.real_count

import com.test.test.common.Resource
import com.test.test.data.remote.dto.real_counts.detail.DetailRealCountResponse
import com.test.test.domain.repository.RealCountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailRealCountUseCase @Inject constructor(
    private val realCountRepository: RealCountRepository
) {
    suspend operator fun invoke(token: String, id: Int): Flow<Resource<DetailRealCountResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = realCountRepository.getDetailRealCount(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
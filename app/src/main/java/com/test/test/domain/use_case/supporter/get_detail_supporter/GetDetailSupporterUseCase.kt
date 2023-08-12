package com.test.test.domain.use_case.supporter.get_detail_supporter

import com.test.test.common.Resource
import com.test.test.data.remote.dto.supporter.detail_supporter.DetailSupporterResponse
import com.test.test.domain.repository.SupporterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailSupporterUseCase @Inject constructor(
    private val supporterRepository: SupporterRepository
) {
    suspend operator fun invoke(token: String, id: Int): Flow<Resource<DetailSupporterResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = supporterRepository.getSupporter(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
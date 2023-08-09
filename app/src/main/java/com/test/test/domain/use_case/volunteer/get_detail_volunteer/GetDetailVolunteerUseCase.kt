package com.test.test.domain.use_case.volunteer.get_detail_volunteer

import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.detail_volunteer.DetailVolunteerResponse
import com.test.test.domain.repository.VolunteerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailVolunteerUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    suspend operator fun invoke(token: String, id: Int): Flow<Resource<DetailVolunteerResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = volunteerRepository.getVolunteer(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
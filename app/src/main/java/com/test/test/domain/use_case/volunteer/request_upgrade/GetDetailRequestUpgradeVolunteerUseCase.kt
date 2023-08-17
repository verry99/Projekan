package com.test.test.domain.use_case.volunteer.request_upgrade

import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.request_upgrade.detail.DetailRequestUpgradeVolunteerResponse
import com.test.test.domain.repository.VolunteerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailRequestUpgradeVolunteerUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int
    ): Flow<Resource<DetailRequestUpgradeVolunteerResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = volunteerRepository.getDetailRequestUpgradeVolunteer(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
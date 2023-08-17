package com.test.test.domain.use_case.volunteer.request_upgrade

import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.request_upgrade.accept_reject.UpdateRequestUpgradeVolunteerResponse
import com.test.test.domain.repository.VolunteerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateRequestUpgradeVolunteerUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int,
        status: String
    ): Flow<Resource<UpdateRequestUpgradeVolunteerResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = volunteerRepository.updateRequestUpgradeVolunteer(token, id, status)
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
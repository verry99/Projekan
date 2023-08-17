package com.test.test.domain.use_case.volunteer.request_upgrade.get_request_upgrade_status

import com.test.test.common.Resource
import com.test.test.data.remote.dto.user.request_upgrade_volunteer.RequestUpgradeVolunteerStatusResponse
import com.test.test.domain.repository.OtherUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestUpgradeVolunteerStatusUseCase @Inject constructor(
    private val otherUserRepository: OtherUserRepository
) {

    suspend operator fun invoke(
        token: String
    ): Flow<Resource<RequestUpgradeVolunteerStatusResponse>> = flow {

        emit(Resource.Loading())
        try {
            val response = otherUserRepository.getRequestUpgradeVolunteer(
                token
            )
            emit(Resource.Success(response))

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
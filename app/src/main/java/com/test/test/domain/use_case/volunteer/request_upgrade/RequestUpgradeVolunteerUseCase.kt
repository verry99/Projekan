package com.test.test.domain.use_case.volunteer.request_upgrade

import com.test.test.common.Resource
import com.test.test.data.remote.dto.user.request_upgrade_volunteer.RequestUpgradeVolunteerStatusResponse
import com.test.test.domain.repository.OtherUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestUpgradeVolunteerUseCase @Inject constructor(
    private val otherUserRepository: OtherUserRepository
) {

    suspend operator fun invoke(
        token: String,
        role: String,
        reason: String
    ): Flow<Resource<RequestUpgradeVolunteerStatusResponse>> = flow {

        emit(Resource.Loading())
        try {
            val response = otherUserRepository.requestUpgradeVolunteer(
                token,
                role,
                reason
            )
            emit(Resource.Success(response))

        } catch (e: HttpException) {
            when (e.code()) {
                in 400..499 -> {
                    emit(Resource.Error("Input salah. Mohon periksa kembali inputan Anda."))
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
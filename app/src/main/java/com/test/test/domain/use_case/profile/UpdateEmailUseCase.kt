package com.test.test.domain.use_case.profile

import com.test.test.common.Resource
import com.test.test.data.remote.dto.profile.update_email.UpdateEmailResponse
import com.test.test.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateEmailUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        token: String,
        password: String,
        email: String
    ): Flow<Resource<UpdateEmailResponse>> = flow {

        emit(Resource.Loading())
        try {
            val response =
                profileRepository.updateEmail(token, password, email)

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
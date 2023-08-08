package com.test.test.domain.use_case.auth.login

import com.test.test.common.Resource
import com.test.test.domain.models.UserPref
import com.test.test.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        email: String,
        password: String,
        deviceToken: String
    ): Flow<Resource<UserPref>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRepository.login(email, password, deviceToken)
            val userPref = UserPref(
                response.user.name,
                response.user.role,
                response.user.profile.photo ?: "",
                response.accessToken
            )
            emit(Resource.Success(userPref))
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
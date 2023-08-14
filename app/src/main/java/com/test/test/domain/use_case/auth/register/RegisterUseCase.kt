package com.test.test.domain.use_case.auth.register

import com.test.test.common.Resource
import com.test.test.domain.models.UserPref
import com.test.test.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String,
        deviceToken: String
    ): Flow<Resource<UserPref>> = flow {
        emit(Resource.Loading())
        try {
            val response = authRepository
                .register(name, email, phone, password, passwordConfirmation, deviceToken)
            val userPref = UserPref(
                response.user.profile?.name!!,
                response.user.role,
                response.user.profile.photo ?: "",
                response.accessToken
            )
            emit(Resource.Success(userPref))
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
package com.test.test.data.repository

import com.test.test.data.remote.api.AuthService
import com.test.test.data.remote.dto.auth.ForgotPasswordResponse
import com.test.test.data.remote.dto.auth.LoginResponse
import com.test.test.data.remote.dto.auth.RegisterResponse
import com.test.test.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String,
        deviceToken: String
    ): RegisterResponse {
        return authService.register(name, email, phone, password, passwordConfirmation, deviceToken)
    }

    override suspend fun login(
        email: String,
        password: String,
        deviceToken: String
    ): LoginResponse {
        return authService.login(email, password, deviceToken)
    }

    override suspend fun forgotPassword(email: String): ForgotPasswordResponse {
        return authService.forgotPassword(email)
    }
}
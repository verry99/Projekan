package com.test.test.domain.repository

import com.test.test.data.remote.dto.auth.LoginResponse
import com.test.test.data.remote.dto.auth.RegisterResponse

interface AuthRepository {
    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String,
        deviceToken: String
    ): RegisterResponse

    suspend fun login(
        email: String,
        password: String,
        deviceToken: String
    ): LoginResponse
}
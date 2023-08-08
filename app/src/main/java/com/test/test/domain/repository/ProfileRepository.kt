package com.test.test.domain.repository

interface ProfileRepository {
    suspend fun getProfile()
    suspend fun postProfile()
}
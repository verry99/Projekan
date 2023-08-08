package com.test.test.domain.repository

import com.test.test.domain.models.UserPref

interface UserPreferenceRepository {

    suspend fun getUserPreference(): UserPref

    suspend fun saveUserPreference(
        name: String,
        role: String,
        urlToImage: String,
        accessToken: String
    )

    suspend fun removeUserPreference()
}
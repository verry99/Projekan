package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : ProfileRepository {
    override suspend fun getProfile() {

    }

    override suspend fun postProfile() {

    }
}
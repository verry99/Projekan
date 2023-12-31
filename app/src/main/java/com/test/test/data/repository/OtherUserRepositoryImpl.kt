package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.user.request_upgrade_volunteer.RequestUpgradeVolunteerStatusResponse
import com.test.test.domain.repository.OtherUserRepository
import javax.inject.Inject

class OtherUserRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : OtherUserRepository {
    override suspend fun getRequestUpgradeVolunteer(token: String): RequestUpgradeVolunteerStatusResponse {
        return dashboardService.getRequestUpgradeVolunteer(token)
    }

    override suspend fun requestUpgradeVolunteer(
        token: String,
        role: String,
        reason: String
    ): RequestUpgradeVolunteerStatusResponse {
        return dashboardService.requestUpgradeVolunteer(token, role, reason)
    }
}
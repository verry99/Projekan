package com.test.test.domain.repository

import com.test.test.data.remote.dto.user.request_upgrade_volunteer.RequestUpgradeVolunteerResponse

interface OtherUserRepository {
    suspend fun getRequestUpgradeVolunteer(
        token: String
    ): RequestUpgradeVolunteerResponse

    suspend fun requestUpgradeVolunteer(
        token: String,
        role: String,
        reason: String
    ): RequestUpgradeVolunteerResponse
}
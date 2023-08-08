package com.test.test.domain.use_case.user_pref.get_user

import com.test.test.domain.models.UserPref
import com.test.test.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class GetUserPreferenceUseCase @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) {
    suspend operator fun invoke(): UserPref {
        return userPreferenceRepository.getUserPreference()
    }
}
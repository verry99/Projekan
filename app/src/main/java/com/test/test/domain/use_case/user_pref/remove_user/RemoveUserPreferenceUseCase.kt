package com.test.test.domain.use_case.user_pref.remove_user

import com.test.test.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class RemoveUserPreferenceUseCase @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) {
    suspend operator fun invoke() {
        userPreferenceRepository.removeUserPreference()
    }
}
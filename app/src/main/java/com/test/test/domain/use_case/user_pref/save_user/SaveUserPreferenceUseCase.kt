package com.test.test.domain.use_case.user_pref.save_user

import com.test.test.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class SaveUserPreferenceUseCase @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) {
    suspend operator fun invoke(
        name: String,
        role: String,
        urlToImage: String,
        accessToken: String
    ) {
        userPreferenceRepository.saveUserPreference(name, role, urlToImage, accessToken)
    }
}
package com.test.test.domain.use_case.profile

import com.test.test.common.Resource
import com.test.test.data.remote.dto.profile.ProfileResponse
import com.test.test.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<ProfileResponse>> = flow {
        emit(Resource.Loading())
        try {
            val data = profileRepository.getProfile(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
        }
    }
}
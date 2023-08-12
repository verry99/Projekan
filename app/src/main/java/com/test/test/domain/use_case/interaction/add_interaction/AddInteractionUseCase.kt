package com.test.test.domain.use_case.interaction.add_interaction

import com.test.test.common.Resource
import com.test.test.data.remote.dto.interaction.add_interaction.AddInteractionResponse
import com.test.test.domain.repository.InteractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddInteractionUseCase @Inject constructor(
    private val interactionRepository: InteractionRepository
) {
    suspend operator fun invoke(
        token: String,
        photo: MultipartBody.Part,
        title: RequestBody,
        description: RequestBody
    ): Flow<Resource<AddInteractionResponse>> = flow {

        emit(Resource.Loading())
        try {
            val response = interactionRepository.addInteraction(
                token,
                photo,
                title,
                description
            )
            emit(Resource.Success(response))

        } catch (e: HttpException) {
            when (e.code()) {
                in 400..499 -> {
                    emit(Resource.Error("Input salah. Mohon periksa kembali inputan Anda."))
                }

                in 500..599 -> {
                    emit(Resource.Error("Server Error."))
                }

                else -> {
                    emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
        }
    }
}
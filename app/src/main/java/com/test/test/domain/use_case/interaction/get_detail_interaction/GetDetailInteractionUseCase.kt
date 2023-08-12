package com.test.test.domain.use_case.interaction.get_detail_interaction

import com.test.test.common.Resource
import com.test.test.data.remote.dto.interaction.detail_interaction.DetailInteractionResponse
import com.test.test.domain.repository.InteractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailInteractionUseCase @Inject constructor(
    private val interactionRepository: InteractionRepository
) {
    suspend operator fun invoke(token: String, id: Int): Flow<Resource<DetailInteractionResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = interactionRepository.getInteraction(token, id)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
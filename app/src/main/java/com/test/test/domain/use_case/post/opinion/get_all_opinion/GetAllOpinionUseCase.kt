package com.test.test.domain.use_case.post.opinion.get_all_opinion

import com.test.test.common.Resource
import com.test.test.data.remote.dto.post.toModel
import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Post
import com.test.test.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllOpinionUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())
        try {
            val data = postRepository.getAllOpinion(token).data.map { it.toModel() }
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
        }
    }
}
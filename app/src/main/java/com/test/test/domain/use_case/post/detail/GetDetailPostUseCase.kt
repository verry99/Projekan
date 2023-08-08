package com.test.test.domain.use_case.post.detail

import com.test.test.common.Resource
import com.test.test.data.remote.dto.detail.DetailPostResponse
import com.test.test.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(token: String, slug: String): Flow<Resource<DetailPostResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = postRepository.getDetailPost(token, slug)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
            }
        }
}
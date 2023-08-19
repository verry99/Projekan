package com.test.test.domain.use_case.post.activities

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.post.PostResponseItem
import com.test.test.domain.repository.PostRepository
import javax.inject.Inject

class GetAllActivitiesUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(token: String): LiveData<PagingData<PostResponseItem>> {
        return postRepository.getAllActivities(token)
    }
}
package com.test.test.domain.use_case.gallery

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.gallery.GalleryResponseItem
import com.test.test.domain.repository.DashboardRepository
import javax.inject.Inject

class GetAllGalleryUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {
    operator fun invoke(token: String): LiveData<PagingData<GalleryResponseItem>> {
        return dashboardRepository.getAllGallery(token)
    }
}
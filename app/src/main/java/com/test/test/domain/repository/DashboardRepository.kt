package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.dashboard.DashboardResponse
import com.test.test.data.remote.dto.gallery.GalleryResponseItem
import com.test.test.data.remote.dto.notification.NotificationResponseItem
import com.test.test.data.remote.dto.notification.detail.DetailNotificationResponse

interface DashboardRepository {
    suspend fun getDashboard(token: String): DashboardResponse

    fun getAllNotification(token: String): LiveData<PagingData<NotificationResponseItem>>

    suspend fun getDetailNotification(token: String, id: Int): DetailNotificationResponse

    fun getAllGallery(token: String): LiveData<PagingData<GalleryResponseItem>>
}
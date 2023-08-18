package com.test.test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.test.data.remote.dto.real_counts.RealCountResponseItem
import com.test.test.data.remote.dto.real_counts.RealCountsResponse
import com.test.test.data.remote.dto.real_counts.add.AddRealCountResponse
import com.test.test.data.remote.dto.real_counts.detail.DetailRealCountResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RealCountRepository {
    fun getAllRealCount(token: String, page: Int): LiveData<PagingData<RealCountResponseItem>>

    suspend fun getDetailRealCount(token: String, id: Int): DetailRealCountResponse

    suspend fun getAllRealCountSummary(token: String): RealCountsResponse

    suspend fun addRealCount(
        token: String,
        image: MultipartBody.Part,
        tps: RequestBody,
        count: RequestBody,
        subDistrict: RequestBody,
        village: RequestBody,
        name: RequestBody,
        voice: RequestBody,
    ): AddRealCountResponse
}
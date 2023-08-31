package com.test.test.data.remote.api

import com.test.test.common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun createRetrofit(baseUrl: String): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getRegionApiService(): RegionService {
        return createRetrofit(Constants.REGION_URL).create(RegionService::class.java)
    }

    fun getAuthApiService(): AuthService {
        return createRetrofit(Constants.BASE_URL).create(AuthService::class.java)
    }

    fun getDashboardApiService(): DashboardService {
        return createRetrofit(Constants.BASE_URL).create(DashboardService::class.java)
    }
}
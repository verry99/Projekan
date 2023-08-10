package com.test.test.data.remote.api

import com.test.test.common.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun createRetrofit(baseUrl: String, headersInterceptor: Interceptor? = null): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .apply {
                headersInterceptor?.let { addInterceptor(it) }
            }
            .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Set read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Set write timeout
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
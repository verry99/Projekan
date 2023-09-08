package com.test.test.di

import com.test.test.data.remote.api.ApiConfig
import com.test.test.data.remote.api.AuthService
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.api.DivisionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRegionService(): DivisionService {
        return ApiConfig.getRegionApiService()
    }

    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return ApiConfig.getAuthApiService()
    }

    @Provides
    @Singleton
    fun provideDashboardService(): DashboardService {
        return ApiConfig.getDashboardApiService()
    }
}
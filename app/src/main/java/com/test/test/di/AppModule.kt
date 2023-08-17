package com.test.test.di

import android.content.Context
import com.test.test.data.remote.api.AuthService
import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.api.RegionService
import com.test.test.data.repository.AnalysisRepositoryImpl
import com.test.test.data.repository.AuthRepositoryImpl
import com.test.test.data.repository.DashboardRepositoryImpl
import com.test.test.data.repository.DivisionRepositoryImpl
import com.test.test.data.repository.InteractionRepositoryImpl
import com.test.test.data.repository.OtherUserRepositoryImpl
import com.test.test.data.repository.PostRepositoryImpl
import com.test.test.data.repository.ProfileRepositoryImpl
import com.test.test.data.repository.RealCountRepositoryImpl
import com.test.test.data.repository.ScheduleRepositoryImpl
import com.test.test.data.repository.SupporterRepositoryImpl
import com.test.test.data.repository.UserPreferenceRepositoryImpl
import com.test.test.data.repository.VolunteerRepositoryImpl
import com.test.test.domain.repository.AnalysisRepository
import com.test.test.domain.repository.AuthRepository
import com.test.test.domain.repository.DashboardRepository
import com.test.test.domain.repository.DivisionRepository
import com.test.test.domain.repository.InteractionRepository
import com.test.test.domain.repository.OtherUserRepository
import com.test.test.domain.repository.PostRepository
import com.test.test.domain.repository.ProfileRepository
import com.test.test.domain.repository.RealCountRepository
import com.test.test.domain.repository.ScheduleRepository
import com.test.test.domain.repository.SupporterRepository
import com.test.test.domain.repository.UserPreferenceRepository
import com.test.test.domain.repository.VolunteerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRegionRepository(regionService: RegionService): DivisionRepository {
        return DivisionRepositoryImpl(regionService)
    }

    @Provides
    @Singleton
    fun providePostRepository(dashboardService: DashboardService): PostRepository {
        return PostRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideVolunteerRepository(dashboardService: DashboardService): VolunteerRepository {
        return VolunteerRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideSupporterRepository(dashboardService: DashboardService): SupporterRepository {
        return SupporterRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideInteractionRepository(dashboardService: DashboardService): InteractionRepository {
        return InteractionRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(dashboardService: DashboardService): ScheduleRepository {
        return ScheduleRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideRealCountRepository(dashboardService: DashboardService): RealCountRepository {
        return RealCountRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(
        @ApplicationContext context: Context
    ): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(dashboardService: DashboardService): DashboardRepository {
        return DashboardRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideAnalysisRepository(dashboardService: DashboardService): AnalysisRepository {
        return AnalysisRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(dashboardService: DashboardService): ProfileRepository {
        return ProfileRepositoryImpl(dashboardService)
    }

    @Provides
    @Singleton
    fun provideOtherUserRepository(dashboardService: DashboardService): OtherUserRepository {
        return OtherUserRepositoryImpl(dashboardService)
    }
}
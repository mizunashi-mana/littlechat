package com.example.littlechat.module

import com.example.littlechat.repository.SessionRepository
import com.example.littlechat.repository.SessionRepositoryImpl
import com.example.littlechat.repository.SettingsRepository
import com.example.littlechat.repository.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module(
    includes = [
        DbModule::class
    ]
)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun provideSessionRepository(impl: SessionRepositoryImpl): SessionRepository

    @Singleton
    @Binds
    abstract fun provideSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}

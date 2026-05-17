package com.example.musicstreamingapp.di

import com.example.musicstreamingapp.data.repository.AuthRepositoryImpl
import com.example.musicstreamingapp.data.repository.DashboardRepositoryImpl
import com.example.musicstreamingapp.domain.repository.AuthRepository
import com.example.musicstreamingapp.domain.repository.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository
}

package com.kyryll.costtracking.di

import com.kyryll.costtracking.data.local.CoinDao
import com.kyryll.costtracking.data.local.CoinDatabase
import com.kyryll.costtracking.data.remote.CoinApi
import com.kyryll.costtracking.data.repository.CoinRepositoryImpl
import com.kyryll.costtracking.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinApi, db: CoinDatabase): CoinRepository {
        return CoinRepositoryImpl(api, db)
    }
}
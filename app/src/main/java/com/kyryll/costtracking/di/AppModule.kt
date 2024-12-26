package com.kyryll.costtracking.di

import android.app.Application
import androidx.room.Room
import com.kyryll.costtracking.data.local.CoinDatabase
import com.kyryll.costtracking.data.local.migrations.MIGRATION_1_2
import com.kyryll.costtracking.data.local.migrations.MIGRATION_2_3
import com.kyryll.costtracking.data.remote.CoinApi
import com.kyryll.costtracking.data.repository.CoinRepositoryImpl
import com.kyryll.costtracking.domain.repository.CoinRepository
import com.kyryll.costtracking.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoinApi(): CoinApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                        }
                    )
                    .build()
            )
            .build()
            .create(CoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(app: Application): CoinDatabase {
        return Room.databaseBuilder(
            app,
            CoinDatabase::class.java,
            "coindb.db"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    }
}
package com.example.data.di

import android.content.Context
import com.example.data.datasource.CitiesDataSource
import com.example.data.datasource.CitiesDataSourceImpl
import com.example.data.repository.CitiesRepositoryImpl
import com.example.domain.repository.CitiesRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun providesCitiesDataSource(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): CitiesDataSource {
        return CitiesDataSourceImpl(
            context = context,
            moshi = moshi
        )
    }

    @Provides
    @Singleton
    fun providesCitiesRepository(
        citiesDataSource: CitiesDataSource
    ): CitiesRepository {
        return CitiesRepositoryImpl(
            dataSource = citiesDataSource
        )
    }
}
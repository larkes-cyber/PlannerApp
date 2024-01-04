package com.example.mywaycompose.di.data

import android.content.Context
import com.example.mywaycompose.data.source.service.ServiceImageStorageDataSource
import com.example.mywaycompose.data.source.service.ServiceImageStorageDataSourceImpl
import com.example.mywaycompose.data.source.service.ServiceSharedPreferenceDataSource
import com.example.mywaycompose.data.source.service.ServiceSharedPreferenceDataSourceImpl
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSource
import com.example.mywaycompose.data.source.user.UserSharedPreferenceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceDataSourceModule {
    @Provides
    fun provideServiceSharedPreferenceDataSource(
        context: Context
    ): ServiceSharedPreferenceDataSource = ServiceSharedPreferenceDataSourceImpl(context = context)

    @Provides
    fun provideServiceImageStorageDataSource(
        context: Context
    ): ServiceImageStorageDataSource = ServiceImageStorageDataSourceImpl(context = context)
    @Provides
    fun provideUserSharedPreferenceDataSource(
        context: Context
    ): UserSharedPreferenceDataSource = UserSharedPreferenceDataSourceImpl(context)
}
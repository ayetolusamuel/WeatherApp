package com.pedek.weatherapp.di

import com.pedek.weatherapp.BuildConfig
import com.pedek.weatherapp.core.api.ApiService
import com.pedek.weatherapp.core.api.ApiServiceFactory
import com.pedek.weatherapp.features.WeatherRemote
import com.pedek.weatherapp.features.remote.WeatherRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    @get:Binds
    val WeatherRemoteImpl.weatherRemote: WeatherRemote

    companion object {
        @[Provides Singleton]
        fun provideApiService(): ApiService =
            ApiServiceFactory.createApiService(BuildConfig.DEBUG,)
    }

}

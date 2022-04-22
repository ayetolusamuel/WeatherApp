package com.pedek.weatherapp.di

import com.pedek.weatherapp.features.domain.WeatherRepositoryImpl
import com.pedek.weatherapp.features.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @get:Binds
    val WeatherRepositoryImpl.weatherRepositoryImpl: WeatherRepository

}

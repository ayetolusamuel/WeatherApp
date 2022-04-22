package com.pedek.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.pedek.weatherapp.core.db.WeatherDb
import com.pedek.weatherapp.features.domain.WeatherResponseLocalCache
import com.pedek.weatherapp.features.domain.repository.impl.WeatherResponseLocalImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalCacheModule {
    @get:Binds
    val WeatherResponseLocalImpl.kegowLocal: WeatherResponseLocalCache

    companion object {
        @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
        @Provides
        fun provideWeatherDatabase(
            @ApplicationContext app: Context,
        ) = Room.databaseBuilder(
            app,
            WeatherDb::class.java,
            "open_weather_database"
        ).fallbackToDestructiveMigration()
            .build() // The reason we can construct a database for the repo

        @Singleton
        @Provides
        fun provideKegowDao(db: WeatherDb) = db.weatherDao()
    }
}

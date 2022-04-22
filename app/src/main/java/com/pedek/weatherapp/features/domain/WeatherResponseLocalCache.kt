package com.pedek.weatherapp.features.domain

import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.model.response.WeeklyRemoteModel
import kotlinx.coroutines.flow.Flow

interface WeatherResponseLocalCache {

    suspend fun saveDailyWeatherResponse(dailyRemoteModel: DailyRemoteModel)
    fun retrieveDailyWeatherResponse(): Flow<DailyRemoteModel>
    suspend fun saveWeeklyWeatherResponse(weeklyRemoteModel: WeeklyRemoteModel)
    fun retrieveWeeklyWeatherResponse(): Flow<WeeklyRemoteModel>

}

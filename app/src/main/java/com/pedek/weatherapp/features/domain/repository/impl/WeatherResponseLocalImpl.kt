package com.pedek.weatherapp.features.domain.repository.impl

import com.pedek.weatherapp.core.db.WeatherDao
import com.pedek.weatherapp.features.domain.WeatherResponseLocalCache
import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.model.response.WeeklyRemoteModel
import javax.inject.Inject

class WeatherResponseLocalImpl @Inject constructor(
    private val weatherDao: WeatherDao,
) : WeatherResponseLocalCache {

    override suspend fun saveDailyWeatherResponse(dailyRemoteModel: DailyRemoteModel) {
        weatherDao.insertDailyResponse(dailyWeatherResponse = dailyRemoteModel)
    }

    override fun retrieveDailyWeatherResponse() = weatherDao.retrieveDailyWeatherResponse()

    override suspend fun saveWeeklyWeatherResponse(weeklyRemoteModel: WeeklyRemoteModel) {
        weatherDao.insertWeeklyResponse(weeklyRemoteModel = weeklyRemoteModel)
    }

    override fun retrieveWeeklyWeatherResponse() = weatherDao.retrieveWeeklyWeatherResponse()

}

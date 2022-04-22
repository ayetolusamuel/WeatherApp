package com.pedek.weatherapp.features.domain.repository

import com.pedek.weatherapp.model.body.WeatherBody
import com.pedek.weatherapp.core.functional.Result
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun dailyWeatherResponse(weatherBody: WeatherBody): Flow<Result<Any>>
    fun weeklyWeatherResponse(weatherBody: WeatherBody): Flow<Result<Any>>
}

package com.pedek.weatherapp.features

import com.pedek.weatherapp.core.functional.Result
import com.pedek.weatherapp.model.body.WeatherBody

interface WeatherRemote {
    suspend fun retrieveDailyWeather(weatherBody: WeatherBody): Result<*>
    suspend fun retrieveWeeklyWeathers(weatherBody: WeatherBody): Result<*>
}

package com.pedek.weatherapp.features.mapper

import com.pedek.weatherapp.model.response.*
import javax.inject.Inject

class WeatherRemoteModelMapper @Inject constructor() {
    private val weathers = mutableListOf<WeatherData>()

    fun mapToDailyRemoteModel(dailyWeatherResponse: DailyWeatherResponse): DailyRemoteModel {
        return DailyRemoteModel(
            name = dailyWeatherResponse.name,
            description = dailyWeatherResponse.weather[0].description,
            pressure = dailyWeatherResponse.main.pressure.toString(),
            humidity = dailyWeatherResponse.main.humidity.toString(),
            temp = dailyWeatherResponse.main.temp.toString(),
            temp_min = dailyWeatherResponse.main.temp_min.toString(),
            temp_max = dailyWeatherResponse.main.temp_max.toString(),
            feelsLike = dailyWeatherResponse.main.feels_like.toString()
        )
    }

    fun mapToWeeklyRemoteModel(weeklyWeatherResponse: WeeklyWeatherResponse): WeeklyRemoteModel {
        mapToWeatherData(weeklyWeatherResponse = weeklyWeatherResponse)
        val weatherList = weathers
        return WeeklyRemoteModel(
            name = weeklyWeatherResponse.city.name,
            weathers = weatherList

        )
    }

    private fun mapToWeatherData(weeklyWeatherResponse: WeeklyWeatherResponse) {
        weeklyWeatherResponse.list.forEach { result ->
            val weatherData = WeatherData()
            weatherData.temp_min = result.main.temp_min.toString()
            weatherData.temp_max = result.main.temp_max.toString()
            result.weather.forEach {
                weatherData.description = it.description
            }
            weatherData.date = result.dt_txt
            weathers.add(weatherData)
        }

    }

}

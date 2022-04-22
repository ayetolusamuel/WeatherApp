package com.pedek.weatherapp.features.states

import com.pedek.weatherapp.model.response.DailyRemoteModel

sealed class DailyWeatherViewState {
    class Success(val message: Int, val weatherResponse: DailyRemoteModel) : DailyWeatherViewState()
    class Failure(val message: String) : DailyWeatherViewState()
    class Loading(val message: Int? = null) : DailyWeatherViewState()
    object Empty : DailyWeatherViewState()
}

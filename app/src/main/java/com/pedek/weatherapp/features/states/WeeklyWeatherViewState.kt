package com.pedek.weatherapp.features.states

import com.pedek.weatherapp.model.response.WeeklyRemoteModel

sealed class WeeklyWeatherViewState {
    class Success(val message: Int, val weatherResponse: WeeklyRemoteModel) :
        WeeklyWeatherViewState()

    class Failure(val message: String) : WeeklyWeatherViewState()
    class Loading(val message: Int? = null) : WeeklyWeatherViewState()
    object Empty : WeeklyWeatherViewState()
}

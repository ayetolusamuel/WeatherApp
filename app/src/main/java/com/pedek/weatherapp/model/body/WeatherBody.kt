package com.pedek.weatherapp.model.body

data class WeatherBody(
    val lat: String,
    val lon: String,
    val units: String = "",
    val cnt: String = "",
    val appid: String,
)
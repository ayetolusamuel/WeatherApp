package com.pedek.weatherapp.model.response


data class WeeklyWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherValue>,
    val message: Int
)
data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class WeatherValue (
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: MainValue,
    val pop: Float,
    val rain: Rain,
    val sys: SysValue,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: WindValue
)

data class WindValue(
    val deg: Int,
    val gust: Double,
    val speed: Double
)
data class SysValue(
    val pod: String
)
data class Rain(
    val `3h`: Double
)



data class MainValue(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
)
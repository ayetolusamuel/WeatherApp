package com.pedek.weatherapp.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_weather_response")
class WeeklyRemoteModel(
    @PrimaryKey(autoGenerate = false)
    val temp: String = "",
    val weathers: List<WeatherData> = listOf(),
    var currentDate: Long = 0L,
    val name: String = "",
)


@Entity
data class WeatherData(
    var date: String = "",
    var pressure: String = "",
    var humidity: String = "",
    var temp_min: String = "",
    var temp_max: String = "",
    var description: String = "",
){
    constructor():this("","","","","","")
}
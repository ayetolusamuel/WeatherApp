package com.pedek.weatherapp.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather_response")
class DailyRemoteModel(
    @PrimaryKey(autoGenerate = false)
    val description: String,
    val temp: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val humidity: String,
    val feelsLike: String,
    var currentDate: Long = 0L,
    val name: String,
)
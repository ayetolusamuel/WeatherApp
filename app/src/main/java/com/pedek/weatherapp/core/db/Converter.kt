package com.pedek.weatherapp.core.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pedek.weatherapp.model.response.WeatherData

class Converter {
    @TypeConverter
    fun weathersToJson(weathers: List<WeatherData>): String = Gson().toJson(weathers)

    @TypeConverter
    fun jsonToWeathers(value: String) = Gson().fromJson(value, Array<WeatherData>::class.java).toList()


}

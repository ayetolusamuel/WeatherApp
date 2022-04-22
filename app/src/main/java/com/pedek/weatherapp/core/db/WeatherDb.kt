package com.pedek.weatherapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.model.response.WeeklyRemoteModel

@Database(
    entities = [
        DailyRemoteModel::class,
        WeeklyRemoteModel::class,
    ],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

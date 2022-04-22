package com.pedek.weatherapp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.model.response.WeeklyRemoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyResponse(dailyWeatherResponse: DailyRemoteModel)

    @Query("SELECT * FROM daily_weather_response")
    fun retrieveDailyWeatherResponse(): Flow<DailyRemoteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyResponse(weeklyRemoteModel: WeeklyRemoteModel)

    @Query("SELECT * FROM weekly_weather_response")
    fun retrieveWeeklyWeatherResponse(): Flow<WeeklyRemoteModel>

}

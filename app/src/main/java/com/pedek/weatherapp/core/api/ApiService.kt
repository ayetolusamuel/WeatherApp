package com.pedek.weatherapp.core.api

import com.pedek.weatherapp.model.response.DailyWeatherResponse
import com.pedek.weatherapp.model.response.WeeklyWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun retrieveWeatherResponseBasedLatLong(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("appid") appId: String,
    ): Response<DailyWeatherResponse>

    @GET("forecast")
    suspend fun retrieveWeatherWeeklyResponse(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("cnt") count: String,
        @Query("units") units: String,
        @Query("appid") appId: String,
    ): Response<WeeklyWeatherResponse>
}


package com.pedek.weatherapp.features.remote

import com.pedek.weatherapp.core.api.ApiService
import com.pedek.weatherapp.core.functional.Failure
import javax.inject.Inject
import com.pedek.weatherapp.core.functional.Result
import com.pedek.weatherapp.core.utils.handleErrorMessage
import com.pedek.weatherapp.features.WeatherRemote
import com.pedek.weatherapp.model.ErrorMessage
import com.pedek.weatherapp.model.body.WeatherBody

class WeatherRemoteImpl @Inject constructor(
    private val apiService: ApiService,
) : WeatherRemote {

    override suspend fun retrieveDailyWeather(weatherBody: WeatherBody): Result<*> {
        return try {
            val res = apiService.retrieveWeatherResponseBasedLatLong(lat = weatherBody.lat,
                lon = weatherBody.lon,
                units = weatherBody.units,
                appId = weatherBody.appid)
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let {
                        Result.Success(it)
                    } ?: Result.Error(Failure.ServerError)
                }
                false -> {
                    val errorMessage = handleErrorMessage(res.errorBody()!!)
                    Result.Failed(errorMessage = errorMessage)
                }
            }
        } catch (e: Throwable) {
            Result.Failed(ErrorMessage(e.message.toString()))
        }
    }

    override suspend fun retrieveWeeklyWeathers(
        weatherBody: WeatherBody,
    ): Result<*> {
        return try {
            val res = apiService.retrieveWeatherWeeklyResponse(lat = weatherBody.lat,
                lon = weatherBody.lon,
                count = weatherBody.cnt,
                appId = weatherBody.appid,
                units = weatherBody.units)
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let {
                        Result.Success(it)
                    } ?: Result.Error(Failure.ServerError)
                }
                false -> {
                    val errorMessage = handleErrorMessage(res.errorBody()!!)
                    Result.Failed(errorMessage = errorMessage)
                }
            }
        } catch (e: Throwable) {
            Result.Failed(e.message.toString())
        }
    }

}



package com.pedek.weatherapp.features.domain


import com.pedek.weatherapp.core.functional.Result
import com.pedek.weatherapp.features.WeatherRemote
import com.pedek.weatherapp.features.domain.repository.WeatherRepository
import com.pedek.weatherapp.features.mapper.WeatherRemoteModelMapper
import com.pedek.weatherapp.model.ErrorMessage
import com.pedek.weatherapp.model.body.WeatherBody
import com.pedek.weatherapp.model.response.DailyWeatherResponse
import com.pedek.weatherapp.model.response.WeeklyWeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemote: WeatherRemote,
    private val mapper: WeatherRemoteModelMapper,
) : WeatherRepository {

    override fun dailyWeatherResponse(weatherBody: WeatherBody): Flow<Result<Any>> {
        return flow {
            when (val res = weatherRemote.retrieveDailyWeather(weatherBody)) {

                is Result.Success -> {
                    if (res.data is DailyWeatherResponse) {
                        emit(Result.Success(mapper.mapToDailyRemoteModel(res.data)))
                    }
                }
                is Result.Failed -> {
                    emit(Result.Failed(res.errorMessage!!))
                }
                else -> {}
            }
        }
    }

    override fun weeklyWeatherResponse(weatherBody: WeatherBody): Flow<Result<Any>> {
        return flow {
            when (val res = weatherRemote.retrieveWeeklyWeathers(weatherBody)) {

                is Result.Success -> {
                    if (res.data is WeeklyWeatherResponse) {
                        emit(Result.Success(mapper.mapToWeeklyRemoteModel(res.data)))
                    }
                }
                is Result.Failed -> {
                    val errorMessage = ErrorMessage(res.errorMessage as String)
                    emit(Result.Failed(errorMessage))
                }
                else -> {}
            }
        }
    }
}

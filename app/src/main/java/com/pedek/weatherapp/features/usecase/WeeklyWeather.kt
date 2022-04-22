package com.pedek.weatherapp.features.usecase

import com.pedek.weatherapp.core.functional.Result
import com.pedek.weatherapp.core.functional.base.FlowUseCase
import com.pedek.weatherapp.features.domain.repository.WeatherRepository
import com.pedek.weatherapp.model.body.WeatherBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeeklyWeather @Inject constructor(
    private val weatherRepository: WeatherRepository
) : FlowUseCase<WeatherBody, Any> {
    override fun invoke(params: WeatherBody?): Flow<Result<Any>> {
        return weatherRepository.weeklyWeatherResponse(params!!)
    }

}

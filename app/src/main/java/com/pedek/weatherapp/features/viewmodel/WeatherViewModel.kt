package com.pedek.weatherapp.features.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedek.weatherapp.R
import com.pedek.weatherapp.core.functional.Result
import com.pedek.weatherapp.features.domain.WeatherResponseLocalCache
import com.pedek.weatherapp.features.states.DailyWeatherViewState
import com.pedek.weatherapp.features.states.WeeklyWeatherViewState
import com.pedek.weatherapp.features.usecase.DailyWeather
import com.pedek.weatherapp.features.usecase.WeeklyWeather
import com.pedek.weatherapp.model.ErrorMessage
import com.pedek.weatherapp.model.body.WeatherBody
import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.model.response.WeeklyRemoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val retrieveDailyWeather: DailyWeather,
    private val retrieveWeeklyWeather: WeeklyWeather,
    private val weatherResponseLocalCache: WeatherResponseLocalCache,
) : ViewModel() {

    private val _dailyWeatherViewFlow =
        MutableStateFlow<DailyWeatherViewState>(DailyWeatherViewState.Empty)
    val dailyWeatherViewFlow: StateFlow<DailyWeatherViewState> = _dailyWeatherViewFlow

    private val _weeklyWeatherViewFlow =
        MutableStateFlow<WeeklyWeatherViewState>(WeeklyWeatherViewState.Empty)
    val weeklyWeatherViewFlow: StateFlow<WeeklyWeatherViewState> = _weeklyWeatherViewFlow

    fun retrieveDailyWeatherResponse(weatherBody: WeatherBody) {
        viewModelScope.launch {
            _dailyWeatherViewFlow.value = DailyWeatherViewState.Loading(R.string.fetching_data)
            retrieveDailyWeather(weatherBody).collectLatest {
                when (it) {
                    is Result.Success -> {
                        if (it.data is DailyRemoteModel) {
                            _dailyWeatherViewFlow.value =
                                DailyWeatherViewState.Success(R.string.successful, it.data)
                        }
                    }
                    is Result.Failed -> {
                        val errorMessage = it.errorMessage as ErrorMessage
                        _dailyWeatherViewFlow.value =
                            DailyWeatherViewState.Failure(errorMessage.message)
                    }
                    else -> {}
                }
            }
        }
    }

    fun retrieveWeeklyWeatherResponse(weatherBody: WeatherBody) {
        viewModelScope.launch {
            _weeklyWeatherViewFlow.value = WeeklyWeatherViewState.Loading(R.string.fetching_data)
            retrieveWeeklyWeather(weatherBody).collectLatest {
                when (it) {
                    is Result.Success -> {
                        if (it.data is WeeklyRemoteModel) {
                            _weeklyWeatherViewFlow.value =
                                WeeklyWeatherViewState.Success(R.string.successful, it.data)
                        }
                    }
                    is Result.Failed -> {
                        val errorMessage = it.errorMessage as ErrorMessage
                        println("Error : $errorMessage")
                        _weeklyWeatherViewFlow.value =
                            WeeklyWeatherViewState.Failure(errorMessage.message)
                    }
                    else -> {}
                }
            }
        }
    }

    fun saveDailyWeatherResponse(dailyRemoteModel: DailyRemoteModel) {
        viewModelScope.launch {
            weatherResponseLocalCache.saveDailyWeatherResponse(dailyRemoteModel = dailyRemoteModel)
        }
    }

    fun retrieveDailyWeatherResponseLocally() =
        weatherResponseLocalCache.retrieveDailyWeatherResponse()

    fun saveWeeklyWeatherResponse(weeklyRemoteModel: WeeklyRemoteModel) {
        viewModelScope.launch {
            weatherResponseLocalCache.saveWeeklyWeatherResponse(weeklyRemoteModel = weeklyRemoteModel)
        }
    }

    fun retrieveWeeklyWeatherResponseLocally() =
        weatherResponseLocalCache.retrieveWeeklyWeatherResponse()

}

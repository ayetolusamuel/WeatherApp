package com.pedek.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope


import com.pedek.weatherapp.BuildConfig
import com.pedek.weatherapp.R
import com.pedek.weatherapp.features.states.DailyWeatherViewState
import com.pedek.weatherapp.features.viewmodel.WeatherViewModel
import com.pedek.weatherapp.model.body.WeatherBody
import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.core.utils.*
import com.pedek.weatherapp.databinding.FragmentDailyWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class DailyWeatherFragment : Fragment(R.layout.fragment_daily_weather) {
    val binding: FragmentDailyWeatherBinding by viewBinding(FragmentDailyWeatherBinding::bind)
    val viewModel: WeatherViewModel by viewModels()
    private lateinit var currentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity = requireActivity() as MainActivity
        currentActivity.supportActionBar?.hide()
        if (requireActivity().isInternetAvailable()) {
            val appId = BuildConfig.APP_ID
            viewModel.retrieveDailyWeatherResponse(WeatherBody(lat = LATITUDE,
                lon = LONGITUDE,
                appid = appId, units = UNITS))
        } else {
            locallyRetrievedData()
            Toast.makeText(requireContext(), "Check internet connection....", Toast.LENGTH_SHORT)
                .show()
        }

        observer()
    }

    private fun locallyRetrievedData() {
        lifecycleScope.launchWhenCreated {
            viewModel.retrieveDailyWeatherResponseLocally().collectLatest {
               if (it != null)attachResponseToView(it)
            }
        }
    }

    private fun observer() {
        lifecycleScope.launchWhenCreated {
            viewModel.dailyWeatherViewFlow.collectLatest {
                when (it) {
                    is DailyWeatherViewState.Loading -> {}
                    is DailyWeatherViewState.Failure -> {
                        println(it.message)
                    }
                    is DailyWeatherViewState.Success -> {
                        val weatherResponse = it.weatherResponse
                        weatherResponse.currentDate = Date().time
                        viewModel.saveDailyWeatherResponse(dailyRemoteModel = it.weatherResponse)
                        attachResponseToView(it.weatherResponse)
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun attachResponseToView(weatherResponse: DailyRemoteModel) {
        val binding = binding
        with(binding) {
            cityTxt.text = weatherResponse.name
            descriptionTxt.text = weatherResponse.description
            dateTxt.text = convertLongDateToWord(weatherResponse.currentDate)
            tempTxt.text = "${weatherResponse.temp} \u2103"
            tempLayout.feelLikeTxt.text = "${weatherResponse.feelsLike} \u2103"
            tempLayout.windTxt.text = "${weatherResponse.temp_min} \u2103"
            tempLayout.humidityTxt.text = "${weatherResponse.humidity} \u2103"
            tempLayout.uvIndexTxt.text = "${weatherResponse.temp_max} \u2103"

        }
    }

    override fun onResume() {
        super.onResume()
      locallyRetrievedData()
    }

}
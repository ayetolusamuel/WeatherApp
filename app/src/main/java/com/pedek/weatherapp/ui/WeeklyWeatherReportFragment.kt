package com.pedek.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedek.weatherapp.R
import com.pedek.weatherapp.databinding.FragmentWeeklyWeatherReportBinding
import com.pedek.weatherapp.BuildConfig
import com.pedek.weatherapp.features.states.WeeklyWeatherViewState
import com.pedek.weatherapp.features.viewmodel.WeatherViewModel
import com.pedek.weatherapp.model.body.WeatherBody
import com.pedek.weatherapp.model.response.DailyRemoteModel
import com.pedek.weatherapp.core.utils.*
import com.pedek.weatherapp.model.response.WeeklyRemoteModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*


@AndroidEntryPoint
class WeeklyWeatherReportFragment : Fragment(R.layout.fragment_weekly_weather_report) {
    val binding: FragmentWeeklyWeatherReportBinding by viewBinding(
        FragmentWeeklyWeatherReportBinding::bind)
    val viewModel: WeatherViewModel by viewModels()
    private lateinit var currentActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentActivity = requireActivity() as MainActivity

        val lat = LATITUDE
        val long = LONGITUDE
        val appId = BuildConfig.APP_ID
        if (requireActivity().isInternetAvailable()) {
            viewModel.retrieveWeeklyWeatherResponse(WeatherBody(lat = lat,
                lon = long,
                cnt = WEEK_UNIT,
                appid = appId,
                units = UNITS
            ))
        } else {
            lifecycleScope.launchWhenCreated {
                viewModel.retrieveWeeklyWeatherResponseLocally().collectLatest {
                    attachResponseToView(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            viewModel.weeklyWeatherViewFlow.collectLatest {
                when (it) {
                    is WeeklyWeatherViewState.Loading -> {}
                    is WeeklyWeatherViewState.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is WeeklyWeatherViewState.Success -> {
                        val weeklyResponse = it.weatherResponse
                        weeklyResponse.currentDate = Date().time
                        viewModel.saveWeeklyWeatherResponse(weeklyResponse)
                        attachResponseToView(it.weatherResponse)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun locallyDailyWeatherData() {
        lifecycleScope.launchWhenCreated {
            viewModel.retrieveDailyWeatherResponseLocally().collectLatest {
                if (it != null) attachDailyResponseToView(it)
            }
        }
    }

    private fun attachDailyResponseToView(dailyWeatherResponse: DailyRemoteModel) {
        val binding = binding
        with(binding) {
            cityTxt.text = dailyWeatherResponse.name
            dateTxt.text = convertLongDateToWord(dailyWeatherResponse.currentDate)
            descriptionTxt.text = dailyWeatherResponse.description
            tempTxt.text = dailyWeatherResponse.temp + " ℃"

        }
    }

    private fun attachResponseToView(weatherResponse: WeeklyRemoteModel) {

        val weathers = weatherResponse.weathers
        val weatherAdapter = WeatherAdapter(weathers)
        with(binding) {
            recyclerView.apply {
                adapter = weatherAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        locallyDailyWeatherData()
        locallyRetrievedData()
    }

    private fun locallyRetrievedData() {
        lifecycleScope.launchWhenCreated {
            viewModel.retrieveWeeklyWeatherResponseLocally().collectLatest {
                if (it != null)attachResponseToView(it)
            }
        }
    }
}

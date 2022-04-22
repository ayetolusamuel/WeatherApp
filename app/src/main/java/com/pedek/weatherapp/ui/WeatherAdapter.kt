package com.pedek.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedek.weatherapp.R
import com.pedek.weatherapp.databinding.WeeklyItemsBinding
import com.pedek.weatherapp.model.response.WeatherData

class WeatherAdapter(
    private val weathers: List<WeatherData>,
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WeeklyItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weathers[position])
    }

    override fun getItemCount(): Int = weathers.size

    inner class ViewHolder(
        private val item: WeeklyItemsBinding,
    ) : RecyclerView.ViewHolder(item.root) {
        fun bind(weatherData: WeatherData) {

            if (weatherData.description.contains("rain")) {
                item.icon.setBackgroundResource(R.drawable.rain)
            } else {
                item.icon.setBackgroundResource(R.drawable.cloud)
            }

            item.description.text = weatherData.description
            item.temp.text = "${weatherData.temp_max}℃/${weatherData.temp_min}℃"
            item.day.text = weatherData.date.substring(0,10)
        }
    }
}

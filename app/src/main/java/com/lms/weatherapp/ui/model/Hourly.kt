package com.lms.weatherapp.ui.model

import com.lms.weatherapp.weather.model.HourlyWeather

data class Hourly(
    val hourlyList : List<HourlyWeather>
)
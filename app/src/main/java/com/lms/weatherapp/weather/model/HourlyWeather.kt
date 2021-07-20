package com.lms.weatherapp.weather.model

import com.lms.weatherapp.common.network.model.weather.TemperatureHourly

data class HourlyWeather(
    val dateTime: String,
    val weatherIcon: Int,
    val iconPhrase: String,
    val temperature: TemperatureHourly,
    val precipitationProb: Int

)
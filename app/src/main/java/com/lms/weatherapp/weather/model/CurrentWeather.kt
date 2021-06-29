package com.lms.weatherapp.weather.model


data class CurrentWeather(
    val temperature: String,
    val realFeelTemperature: String,
    val visibility: String,
    val uVIIndex: String,
    val wind: String,
    val weatherIcon: Int
)
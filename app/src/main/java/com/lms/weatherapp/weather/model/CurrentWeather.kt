package com.lms.weatherapp.weather.model


data class CurrentWeather(
    val localObservationDateTime: String,
    val temperature: String,
    val weatherIcon: Int,
    val weatherText: String
)
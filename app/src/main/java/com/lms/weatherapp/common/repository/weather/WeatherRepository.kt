package com.lms.weatherapp.common.repository.weather

import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather


interface WeatherRepository {
    fun getWeatherByLocationKey(callback: RepositoryCallback<List<CurrentWeather>, String>)
    fun getLocationKey(): String
}


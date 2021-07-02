package com.lms.weatherapp.weather.repository

import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather


interface WeatherRepository {
    fun getWeatherByLocationKey(callback: RepositoryCallback<List<CurrentWeather>, String>)
    fun getLocationKey(): String
    fun getLocationName(): String
    fun get5DaysForecast(callback: RepositoryCallback<ForecastWeather, String>)
}


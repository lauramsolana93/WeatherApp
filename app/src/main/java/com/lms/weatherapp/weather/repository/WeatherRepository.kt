package com.lms.weatherapp.weather.repository

import com.lms.weatherapp.common.network.model.weather.CurrentConditionsResponse
import com.lms.weatherapp.common.network.model.weather.ForecastResponse
import com.lms.weatherapp.common.network.model.weather.HourlyResponse
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather


interface WeatherRepository {
    suspend fun getWeatherByLocationKey(): List<CurrentConditionsResponse>
    fun getLocationKey(): String
    fun getLocationName(): String
    suspend fun get5DaysForecast(): ForecastResponse
    suspend fun getHourly12Hours(): List<HourlyResponse>
}


package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather


interface WeatherFactory {

    suspend fun getWeatherByLocation(): CurrentWeather
    suspend fun get5DaysForecast(): ForecastWeather
    fun getCurrentLocationName() : String
    suspend fun getHourly12Hours(): List<HourlyWeather>

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}
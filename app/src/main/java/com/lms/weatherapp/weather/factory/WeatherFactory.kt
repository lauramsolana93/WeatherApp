package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.weather.model.CurrentWeather


interface WeatherFactory {

    suspend fun getWeatherByLocation(): CurrentWeather
    fun get5DaysForecast(callback: Callback)
    fun getCurrentLocationName() : String
    fun getHourly12Hours(callback: Callback)

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}
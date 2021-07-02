package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.location.model.Location


interface WeatherFactory {

    fun getWeatherByLocation(callback: Callback)
    fun get5DaysForecast(callback: Callback)
    fun getCurrentLocationName() : String

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}
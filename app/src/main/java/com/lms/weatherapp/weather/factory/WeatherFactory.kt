package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.weather.model.Location


interface WeatherFactory {

    fun buildWeather(callback: Callback, location: String)
    fun getWeatherByLocation(callback: Callback, location: Location)

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}
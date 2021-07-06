package com.lms.weatherapp.weather.factory


interface WeatherFactory {

    fun getWeatherByLocation(callback: Callback)
    fun get5DaysForecast(callback: Callback)
    fun getCurrentLocationName() : String
    fun getHourly12Hours(callback: Callback)

    interface Callback {
        fun onSuccess(any:Any)
        fun onError(e: String)
        fun onLoading(isLoading: Boolean)
    }
}